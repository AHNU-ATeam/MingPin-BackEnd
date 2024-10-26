package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.service.TeacherService;
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

    public TeacherServiceImpl(
            TeacherMapper teacherMapper,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.teacherMapper = teacherMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Result add(CampusTeacherVO campusTeacherVo) {
        Teacher teacher = new Teacher();
        User user = new User();
        BeanUtils.copyProperties(campusTeacherVo, teacher);
        BeanUtils.copyProperties(campusTeacherVo, user);
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

        return Result.success("创建成功");
    }

    @Override
    public List<TeacherVoForShow> getAllTeacherUsers() {
        // 查询所有教师信息
        List<Teacher> teacherList = teacherMapper.selectList(new QueryWrapper<>());

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
            return Result.error("创建失败");
        }

        if(userMapper.updateById(user) == 0){
            return Result.error("创建失败");
        }

        return Result.success("创建成功");

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
}
