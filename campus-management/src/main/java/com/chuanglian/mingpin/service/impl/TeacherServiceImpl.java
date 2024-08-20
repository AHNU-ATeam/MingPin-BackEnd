package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void add(Teacher teacher) {
        teacher.setUpdatedAt(LocalDateTime.now());
        teacherMapper.add(teacher);
    }

    @Override
    public List<Teacher> list() {
        return teacherMapper.list();
    }

    @Override
    public void delete(Integer id) {
        teacherMapper.delete(id);
    }

    @Override
    public void update(Teacher teacher) {
        teacher.setUpdatedAt(LocalDateTime.now());
        teacherMapper.update(teacher);
    }

    @Override
    public Teacher getTeacherById(Long teacherId) {
        return teacherMapper.selectTeacherById(teacherId);
    }
}
