package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.campus.ClassStudent;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.entity.user.dto.StudentDTO;
import com.chuanglian.mingpin.mapper.campus.ClassStudentMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final ClassStudentMapper classStudentMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, UserMapper userMapper, ClassStudentMapper classStudentMapper, PasswordEncoder passwordEncoder) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
        this.classStudentMapper = classStudentMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Student> campusList(Integer campusId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getCampusId, campusId)
                .ne(Student::getStatus, 1);
        return studentMapper.selectList(queryWrapper);
    }

    @Override
    public List<Student> classList(Integer classId) {
        LambdaQueryWrapper<ClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassStudent::getClassId, classId);
        List<ClassStudent> classStudents = classStudentMapper.selectList(queryWrapper);
        // 提取出所有学生的 ID
        List<Integer> studentIds = classStudents.stream()
                .map(ClassStudent::getStudentId)
                .toList();
        // 根据学生 ID 批量查询学生信息
        return studentMapper.selectBatchIds(studentIds);
    }
    @Override
    public Student findById(Integer studentId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentId, studentId)
                .ne(Student::getStatus, 1);
        return studentMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public void add(StudentDTO studentDTO) {
        User user = new User();
        Student student = new Student();
        user.setBoundPhone(studentDTO.getParentPhone());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setNickname(studentDTO.getStudentName());
        user.setStatus("enable");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(user);

        BeanUtils.copyProperties(studentDTO, student);
        student.setUserId(user.getId());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public void update(Student student) {
        // 设置更新时间
        student.setUpdatedAt(LocalDateTime.now());

        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();

        // 可以根据需要添加更多条件
        wrapper.eq(Student::getStudentId, student.getStudentId()); // 假设使用 id 作为唯一标识符

        // 使用 UpdateWrapper 进行更新
        studentMapper.update(student, wrapper);
    }

    @Override
    public void deleteById(Integer studentId) {
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getStudentId, studentId);
        wrapper.set(Student::getStatus, 1);
        studentMapper.update(null, wrapper);

    }
}
