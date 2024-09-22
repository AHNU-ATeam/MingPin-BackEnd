package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.TeacherVO;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public Result add(TeacherVO teacherVo) {
        Teacher teacher = new Teacher();
        User user = new User();
        BeanUtils.copyProperties(teacherVo, teacher);
        BeanUtils.copyProperties(teacherVo, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        if (userMapper.insert(user) == 0) {
            throw new IllegalStateException("创建失败");
        }

        int id = user.getId();
        teacher.setUserId(id);

        if (teacherMapper.add(teacher) == 0) {
            return Result.error("创建失败");
        }

        return Result.success("创建成功");
    }

    @Override
    public Result list() {
        if(teacherMapper.list() == null ){
            return Result.error("查询失败 ");
        }
        return Result.success(teacherMapper.list());
    }

    @Override
    public Result delete(Integer teacherId) {
        if(teacherMapper.delete(teacherId) < 0){
            return Result.error("删除失败");
        }

        return Result.success("删除成功");
    }

    @Override
    public Result update(TeacherVO teacherVO) {
//        TODO
//        Teacher teacher = new Teacher(teacherVO);
//        teacher.setTeacherId(Math.toIntExact(teacherVO.getTeacherId()));
//        teacher.setUpdatedAt(LocalDateTime.now());
//
//        System.out.println(teacherVO.getPassword());
//
//        if(teacherMapper.updateById(teacher) == 0){
//            throw new IllegalStateException("更新失败");
//        }
        return Result.success("更新成功");
    }

    @Override
    public Result getTeacherById(Integer teacherId) {
        if(teacherMapper.selectTeacherById(teacherId) == null){
            return Result.error("查无此人");
        }
        return Result.success(teacherMapper.selectTeacherById(teacherId));
    }
}
