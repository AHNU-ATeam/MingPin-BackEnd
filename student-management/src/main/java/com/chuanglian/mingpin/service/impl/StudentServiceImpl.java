package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.campus.ClassStudent;
import com.chuanglian.mingpin.entity.permission.UserRole;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.entity.user.dto.StudentDTO;
import com.chuanglian.mingpin.entity.user.dto.UpdateStudentDTO;
import com.chuanglian.mingpin.entity.user.vo.StudentInfoVO;
import com.chuanglian.mingpin.entity.user.vo.StudentVO;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.campus.ClassStudentMapper;
import com.chuanglian.mingpin.mapper.permission.UserRoleMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final ClassStudentMapper classStudentMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClassMgmtMapper classMgmtMapper;
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, UserMapper userMapper, ClassStudentMapper classStudentMapper, PasswordEncoder passwordEncoder, ClassMgmtMapper classMgmtMapper, UserRoleMapper userRoleMapper) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
        this.classStudentMapper = classStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.classMgmtMapper = classMgmtMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<StudentVO> campusList(Integer campusId) {
        return getStudentVOSByCondition(new LambdaQueryWrapper<Student>()
                .eq(Student::getCampusId, campusId)
                .ne(Student::getStatus, 1));
    }

    @Override
    public List<StudentVO> classList(Integer classId) {
        List<Integer> studentIds = classStudentMapper.selectList(new LambdaQueryWrapper<ClassStudent>()
                        .eq(ClassStudent::getClassId, classId))
                .stream()
                .map(ClassStudent::getStudentId)
                .toList();

        return getStudentVOSByCondition(new LambdaQueryWrapper<Student>()
                .in(Student::getUserId, studentIds)
                .eq(Student::getStatus, 0), classId);
    }

    @Override
    public StudentInfoVO findById(Integer studentId) {
        Student student = getStudentById(studentId);
        User user = getUserById(studentId);
        ClassStudent classStudent = getClassStudentByStudentId(studentId);

        return buildStudentInfoVO(student, user, classStudent);
    }

    @Override
    @Transactional
    public void add(StudentDTO studentDTO) {
        User user = createUser(studentDTO);
        studentMapper.insert(createStudent(studentDTO, user.getId()));
        classStudentMapper.insert(createClassStudent(user.getId(), studentDTO.getClassId()));
        userRoleMapper.insert(createUserRole(user.getId(), 3));
    }

    @Override
    public void update(UpdateStudentDTO updateStudentDTO) {
        updateStudentInfo(updateStudentDTO);
        if (updateStudentDTO.getClassId() != null) {
            updateClassStudent(updateStudentDTO.getUserId(), updateStudentDTO.getClassId());
        }
    }

    @Override
    public void deleteById(Integer studentId) {
        updateStudentStatus(studentId, 1);
        updateUserStatus(studentId, "disable");
    }

    @Override
    public List<StudentVO> keyWordList(String keyWord) {
        List<Integer> userIds = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .like(User::getNickname, keyWord)
                        .eq(User::getStatus, "enable"))
                .stream()
                .map(User::getId)
                .toList();

        return getStudentVOSByCondition(new LambdaQueryWrapper<Student>()
                .in(Student::getUserId, userIds)
                .ne(Student::getStatus, 1));
    }

    private List<StudentVO> getStudentVOSByCondition(LambdaQueryWrapper<Student> queryWrapper) {
        return getStudentVOSByCondition(queryWrapper, null);
    }

    private List<StudentVO> getStudentVOSByCondition(LambdaQueryWrapper<Student> queryWrapper, Integer classId) {
        List<Student> students = studentMapper.selectList(queryWrapper);
        List<StudentVO> studentVOS = new ArrayList<>();

        for (Student student : students) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(student, studentVO);
            User user = getUserById(student.getUserId());
            studentVO.setUserName(user.getNickname());

            ClassStudent classStudent = getClassStudentByStudentId(student.getUserId());
            studentVO.setClassId(classId != null ? classId : classStudent.getClassId());
            studentVO.setMingPinClassName(getClassNameById(classStudent.getClassId()));

            studentVOS.add(studentVO);
        }

        return studentVOS;
    }

    private Student getStudentById(Integer studentId) {
        return studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getUserId, studentId)
                .ne(Student::getStatus, 1));
    }

    private User getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    private ClassStudent getClassStudentByStudentId(Integer studentId) {
        return classStudentMapper.selectOne(new LambdaQueryWrapper<ClassStudent>()
                .eq(ClassStudent::getStudentId, studentId));
    }

    private String getClassNameById(Integer classId) {
        return classMgmtMapper.selectById(classId).getName();
    }

    private StudentInfoVO buildStudentInfoVO(Student student, User user, ClassStudent classStudent) {
        StudentInfoVO studentInfoVO = new StudentInfoVO();
        BeanUtils.copyProperties(student, studentInfoVO);
        studentInfoVO.setUserName(user.getNickname());
        studentInfoVO.setClassId(classStudent.getClassId());
        studentInfoVO.setMingPinClassName(getClassNameById(classStudent.getClassId()));
        return studentInfoVO;
    }

    private User createUser(StudentDTO studentDTO) {
        User user = new User();
        user.setBoundPhone(studentDTO.getParentPhone());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setNickname(studentDTO.getStudentName());
        user.setStatus("enable");
        userMapper.insert(user);
        return user;
    }

    private Student createStudent(StudentDTO studentDTO, Integer userId) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setUserId(userId);
        return student;
    }

    private ClassStudent createClassStudent(Integer studentId, Integer classId) {
        ClassStudent classStudent = new ClassStudent();
        classStudent.setStudentId(studentId);
        classStudent.setClassId(classId);
        return classStudent;
    }

    private UserRole createUserRole(Integer userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

    private void updateStudentInfo(UpdateStudentDTO updateStudentDTO) {
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getUserId, updateStudentDTO.getUserId()));
        BeanUtils.copyProperties(updateStudentDTO, student);
        studentMapper.updateById(student);
    }

    private void updateClassStudent(Integer studentId, Integer classId) {
        classStudentMapper.update(null, new LambdaUpdateWrapper<ClassStudent>()
                .eq(ClassStudent::getStudentId, studentId)
                .set(ClassStudent::getClassId, classId));
    }

    private void updateStudentStatus(Integer studentId, Integer status) {
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getUserId, studentId)
                .eq(Student::getStatus, 0));
        student.setStatus(status);
        studentMapper.updateById(student);
    }

    private void updateUserStatus(Integer userId, String status) {
        User user = userMapper.selectById(userId);
        user.setStatus(status);
        userMapper.updateById(user);
    }
}
