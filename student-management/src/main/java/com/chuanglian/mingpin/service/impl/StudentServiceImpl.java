package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.campus.ClassStudent;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.entity.user.dto.StudentDTO;
import com.chuanglian.mingpin.entity.user.dto.UpdateStudentDTO;
import com.chuanglian.mingpin.entity.user.vo.StudentInfoVO;
import com.chuanglian.mingpin.entity.user.vo.StudentVO;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
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
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final ClassStudentMapper classStudentMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClassMgmtMapper classMgmtMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, UserMapper userMapper, ClassStudentMapper classStudentMapper, PasswordEncoder passwordEncoder, ClassMgmtMapper classMgmtMapper) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
        this.classStudentMapper = classStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.classMgmtMapper = classMgmtMapper;
    }

    @Override
    public List<StudentVO> campusList(Integer campusId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getCampusId, campusId)
                .ne(Student::getStatus, 1);
        List<Student> students = studentMapper.selectList(queryWrapper);
        List<StudentVO> studentVOS = new ArrayList<>();
        for ( Student student : students ) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student, studentVO);
            User user = userMapper.selectById(student.getUserId());
            studentVO.setUserName(user.getNickname());
            LambdaQueryWrapper<ClassStudent> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ClassStudent::getStudentId, student.getUserId());
            ClassStudent classStudent = classStudentMapper.selectOne(queryWrapper1);
            studentVO.setClassId(classStudent.getClassId());
            Class c = classMgmtMapper.selectById(classStudent.getClassId());
            studentVO.setMingPinClassName(c.getName());
            studentVOS.add(studentVO);
        }
        return studentVOS;
    }

    @Override
    public List<StudentVO> classList(Integer classId) {
        LambdaQueryWrapper<ClassStudent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassStudent::getClassId, classId);
        List<ClassStudent> classStudents = classStudentMapper.selectList(queryWrapper);
        // 提取出所有学生的 ID
        List<Integer> studentIds = classStudents.stream()
                .map(ClassStudent::getStudentId)
                .toList();
        // 根据学生 ID 批量查询学生信息
        List<Student> students = studentMapper.selectBatchIds(studentIds);
        List<StudentVO> studentVOS = new ArrayList<>();
        for (Student student : students ) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student, studentVO);
            User user = userMapper.selectById(student.getUserId());
            studentVO.setUserName(user.getNickname());
            studentVO.setClassId(classId);
            Class c = classMgmtMapper.selectById(classId);
            studentVO.setMingPinClassName(c.getName());
            studentVOS.add(studentVO);
        }
        return studentVOS;
    }
    @Override
    public StudentInfoVO findById(Integer studentId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserId, studentId)
                .ne(Student::getStatus, 1);
        StudentInfoVO studentInfoVO = new StudentInfoVO();
        Student student = studentMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(student, studentInfoVO);
        User user = userMapper.selectById(studentId);
        studentInfoVO.setUserName(user.getNickname());
        LambdaQueryWrapper<ClassStudent> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ClassStudent::getStudentId, studentId);
        ClassStudent classStudent = classStudentMapper.selectOne(queryWrapper1);
        studentInfoVO.setClassId(classStudent.getClassId());
        Class c = classMgmtMapper.selectById(classStudent.getClassId());
        studentInfoVO.setMingPinClassName(c.getName());
        return studentInfoVO;
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
        ClassStudent classStudent = new ClassStudent();
        classStudent.setStudentId(user.getId());
        classStudent.setClassId(studentDTO.getClassId());
        studentMapper.insert(student);
    }

    @Override
    public void update(UpdateStudentDTO updateStudentDTO) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserId, updateStudentDTO.getUserId());
        Student student = studentMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(updateStudentDTO, student);
        LambdaUpdateWrapper<Student> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Student::getUserId, student.getUserId());
        studentMapper.update(student, updateWrapper);
    }

    @Override
    public void deleteById(Integer studentId) {
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getUserId, studentId);
        wrapper.set(Student::getStatus, 1);
        studentMapper.update(null, wrapper);
    }
}
