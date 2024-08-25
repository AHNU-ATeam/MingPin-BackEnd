package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Override
    public List<Student> campusList(Integer campusId) {
        return studentMapper.selectCampusList(campusId);
    }

    @Override
    public List<Student> classList(Integer classId) {
        return studentMapper.selectClassList(classId);
    }
    @Override
    public Student findById(Integer studentId) {
        return studentMapper.selectById(studentId);
    }

    @Override
    public void add(Student student) {
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public void update(Student student) {
        // 设置更新时间
        student.setUpdatedAt(LocalDateTime.now());

        // 创建 UpdateWrapper 实例
        UpdateWrapper<Student> wrapper = new UpdateWrapper<>();

        // 可以根据需要添加更多条件
        wrapper.eq("student_id", student.getStudentId()); // 假设使用 id 作为唯一标识符

        // 使用 UpdateWrapper 进行更新
        studentMapper.update(student, wrapper);
    }

    @Override
    public void deleteById(Integer studentId) {
        studentMapper.deleteById(studentId);
    }
}
