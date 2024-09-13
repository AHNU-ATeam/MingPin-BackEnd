package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
    private StudentMapper studentMapper;

    @Override
    public List<Student> campusList(Integer campusId) {
        // 使用LambdaQueryWrapper来构建查询条件，并排除status为1的记录
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getCampusId, campusId).ne(Student::getStatus, 1); // 排除status为1的记录

        return studentMapper.selectList(queryWrapper);
    }

    @Override
    public List<Student> classList(Integer classId) {
        // 使用LambdaQueryWrapper来构建查询条件，并排除status为1的记录
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getClassId, classId).ne(Student::getStatus, 1); // 排除status为1的记录

        return studentMapper.selectList(queryWrapper);
    }

    @Override
    public Student findById(Integer studentId) {
        // 使用LambdaQueryWrapper直接排除已删除的记录
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentId, studentId).ne(Student::getStatus, 1); // 排除status为1的记录

        return studentMapper.selectOne(queryWrapper);
    }

    @Override
    public void add(Student student) {
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        student.setStatus(0); // 默认状态为未删除
        studentMapper.insert(student);
    }

    @Override
    public void update(Student student) {
        // 设置更新时间
        student.setUpdatedAt(LocalDateTime.now());

        // 创建 LambdaUpdateWrapper 实例
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getStudentId, student.getStudentId()); // 使用 id 作为唯一标识符

        // 使用 LambdaUpdateWrapper 进行更新
        studentMapper.update(student, wrapper);
    }

    @Override
    public void deleteById(Integer studentId) {
        // 使用LambdaUpdateWrapper来构建更新条件，并将status设置为1表示删除
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getStudentId, studentId);
        wrapper.set(Student::getStatus, 1); // 将status设置为1表示删除

        studentMapper.update(null, wrapper);
    }
}