package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.entity.permission.UserRole;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.CampMapper;
import com.chuanglian.mingpin.mapper.permission.UserRoleMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.service.TeacherService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final CampMapper campMapper;

    private final UserRoleMapper userRoleMapper;

    public TeacherServiceImpl(
            TeacherMapper teacherMapper,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            CampMapper campMapper,
            UserRoleMapper userRoleMapper
    ) {
        this.teacherMapper = teacherMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.campMapper = campMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    @Transactional
    public Result add(CampusTeacherVO campusTeacherVo) {
        Teacher teacher = new Teacher();
        User user = new User();
        BeanUtils.copyProperties(campusTeacherVo, teacher);
        BeanUtils.copyProperties(campusTeacherVo, user);

        if (!isValidPhoneNumber(campusTeacherVo.getBoundPhone())) {
            return Result.error("手机号格式不正确");
        }

        if (!isValidIdentificationNumber(campusTeacherVo.getIdentificationNumber())) {
            return Result.error("身份证号码格式不正确");
        }

        // 校验 campusId 是否存在
        int campusCount = Math.toIntExact(campMapper.selectCount(new QueryWrapper<Campus>().lambda().eq(Campus::getCampusId, campusTeacherVo.getCampusId())));
        if (campusCount == 0) {
            return Result.error("校园ID不存在，无法添加该教师信息");
        }

        int count = Math.toIntExact(userMapper.selectCount(new QueryWrapper<User>().lambda().eq(User::getBoundPhone, campusTeacherVo.getBoundPhone())));
        if (count > 0) {
            return Result.error("该手机号已被绑定，无法重复添加用户");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        teacher.setStatus(1);
        user.setStatus("enable");
        teacher.setPosition("教师");

        if (userMapper.insert(user) == 0) {
            throw new IllegalStateException("创建失败");
        }

        int id = user.getId();
        teacher.setUserId(id);

        if (teacherMapper.insert(teacher) == 0) {
            return Result.error("创建失败");
        }

        //赋老师权限
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRole.setRoleId(2);

        userRoleMapper.insert(userRole);


        return Result.success(id);

    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[1][3-9]\\d{9}$";
        return phoneNumber != null && phoneNumber.matches(regex);
    }

    public boolean isValidIdentificationNumber(String identificationNumber) {
        String regex = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}[0-9Xx]$";
        return identificationNumber != null && identificationNumber.matches(regex);
    }


    @Override
    public List<TeacherVoForShow> getAllTeacherUsers(Integer campusId) {
        // 查询status为1且campus_id为指定值的所有教师信息
        List<Teacher> teacherList = teacherMapper.selectList(
                new QueryWrapper<Teacher>()
                        .eq("status", 1)
                        .eq("campus_id", campusId)
        );


        // 遍历每个教师信息，获取对应的用户信息并合并到 DTO 中
        return teacherList.stream().map(teacher -> {
            // 查询对应的用户信息
            User user = userMapper.selectById(teacher.getUserId());

            // 创建 DTO 对象，并将 Teacher 和 User 数据整合
            TeacherVoForShow teacherVoForShow = new TeacherVoForShow();

            // 通过 BeanUtils 拷贝用户信息到 DTO 中
            if (user != null) {
                BeanUtils.copyProperties(user, teacherVoForShow);
                BeanUtils.copyProperties(teacher, teacherVoForShow);
            }


            return teacherVoForShow;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Result delete(Integer teacherId) {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherId);
        teacher.setStatus(0);

        User user = new User();
        user.setId(teacherMapper.getUserIdByTeacherId(teacherId));
        user.setStatus("disable");

        if(teacherMapper.updateById(teacher) == 0){
            return Result.error("删除失败");
        }
        if(userMapper.updateById(user) == 0){
            return Result.error("删除失败");
        }

        return Result.success("删除成功");
    }

    @Override
    public Result update(TeacherVoForUpdate teacherVoForUpdate) {
       Teacher teacher = new Teacher();
        User user = new User();
        BeanUtils.copyProperties(teacherVoForUpdate, teacher);
        BeanUtils.copyProperties(teacherVoForUpdate, user);
        user.setId(teacherMapper.getUserIdByTeacherId(teacherVoForUpdate.getTeacherId()));
        teacher.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        if(teacherMapper.updateById(teacher) == 0){
            return Result.error("更新失败");
        }

        if(userMapper.updateById(user) == 0){
            return Result.error("更新失败");
        }

        return Result.success("更新成功");

    }

    @Override
    public Result getTeacherById(Integer teacherId) {

        User user = new User();
        user.setId(teacherMapper.getUserIdByTeacherId(teacherId));
        user = userMapper.selectById(user.getId());
        Teacher teacher =  teacherMapper.selectTeacherById(teacherId);
        TeacherVoForShow teacherVoForShow = new TeacherVoForShow();
        BeanUtils.copyProperties(teacher, teacherVoForShow);
        BeanUtils.copyProperties(user, teacherVoForShow);

        return Result.success(teacherVoForShow);
    }

    @Override
    public Result getTeacherByUserId(Integer userId) {

        User user = new User();
        user.setId(userId);
        user = userMapper.selectById(user.getId());
                QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq("user_id", userId);
        // 执行查询
        Teacher teacher =  teacherMapper.selectOne(queryWrapper);
        TeacherVoForShow teacherVoForShow = new TeacherVoForShow();
        BeanUtils.copyProperties(teacher, teacherVoForShow);
        BeanUtils.copyProperties(user, teacherVoForShow);

        return Result.success(teacherVoForShow);
    }
}
