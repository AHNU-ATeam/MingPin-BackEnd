package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.entity.user.vo.StudentVo;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
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
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getClassId, classId)
                .ne(Student::getStatus, 1);
        return studentMapper.selectList(queryWrapper);
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
    public void add(StudentVo studentVo) {
        User user = new User();
        Student student = new Student();
        user.setBoundPhone(studentVo.getParentPhone());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setNickname(studentVo.getStudentName());
        user.setStatus("enable");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(user);

        student.setUserId(user.getId());
        student.setGender(studentVo.getGender());
        student.setBirthDate(studentVo.getBirthDate());
        student.setCalendarType(studentVo.getCalendarType());
        student.setSchoolName(studentVo.getSchoolName());
        student.setGradeName(studentVo.getGradeName());
        student.setClassName(studentVo.getClassName());
        student.setParentName(studentVo.getParentName());
        student.setParentPhone(studentVo.getParentPhone());
        student.setAddress(studentVo.getAddress());
        student.setEmergencyContactPhone(studentVo.getEmergencyContactPhone());
        student.setSpecialCondition(studentVo.getSpecialCondition());
        student.setServiceItem(studentVo.getServiceItem());
        student.setParentSuggestions(studentVo.getParentSuggestions());
        student.setStudentSummary(studentVo.getStudentSummary());
        student.setAdmissionDate(studentVo.getAdmissionDate());
        student.setTeacherName(studentVo.getTeacherName());
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
