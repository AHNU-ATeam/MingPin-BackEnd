package com.mingpin.service.impl;

import com.mingpin.mapper.TeacherMapper;
import com.mingpin.pojo.Teacher;
import com.mingpin.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void add(Teacher teacher) {
        teacher.setUpdatedAt(LocalDate.now());
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
        teacher.setUpdatedAt(LocalDate.now());
        teacherMapper.update(teacher);
    }

    @Override
    public Teacher getTeacherById(Long teacherId) {
        return teacherMapper.selectTeacherById(teacherId);
    }
}
