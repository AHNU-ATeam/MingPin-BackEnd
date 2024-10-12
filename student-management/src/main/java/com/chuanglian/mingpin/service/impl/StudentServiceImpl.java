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
import com.chuanglian.mingpin.mapper.point.PointMapper;
import com.chuanglian.mingpin.mapper.point.PointRecordsMapper;
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
    private final PointMapper pointMapper;
    private final PointRecordsMapper pointRecordsMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, UserMapper userMapper, ClassStudentMapper classStudentMapper, PasswordEncoder passwordEncoder, ClassMgmtMapper classMgmtMapper, UserRoleMapper userRoleMapper, PointMapper pointMapper, PointRecordsMapper pointRecordsMapper) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
        this.classStudentMapper = classStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.classMgmtMapper = classMgmtMapper;
        this.userRoleMapper = userRoleMapper;
        this.pointMapper = pointMapper;
        this.pointRecordsMapper = pointRecordsMapper;
    }

    /**
     * 根据校区 ID 获取该校区的学生列表
     * @param campusId 校区 ID
     * @return 学生信息列表
     */
    @Override
    public List<StudentVO> campusList(Integer campusId) {
        return getStudentVOSByCondition(new LambdaQueryWrapper<Student>()
                .eq(Student::getCampusId, campusId)
                .ne(Student::getStatus, 1));
    }

    /**
     * 根据班级 ID 获取该班级的学生列表
     * @param classId 班级 ID
     * @return 学生信息列表
     */
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

    /**
     * 根据学生 ID 查询学生详细信息
     * @param studentId 学生 ID
     * @return 学生详细信息 VO 对象
     */
    @Override
    public StudentInfoVO findById(Integer studentId) {
        Student student = getStudentById(studentId);
        User user = getUserById(studentId);
        ClassStudent classStudent = getClassStudentByStudentId(studentId);

        return buildStudentInfoVO(student, user, classStudent);
    }

    /**
     * 新增学生，并更新班级人数
     * @param studentDTO 新增学生的 DTO 对象
     */
    @Override
    @Transactional
    public void add(StudentDTO studentDTO) {
        // 创建新用户
        User user = createUser(studentDTO);
        // 创建学生记录
        studentMapper.insert(createStudent(studentDTO, user.getId()));
        // 在班级学生表中插入记录
        classStudentMapper.insert(createClassStudent(user.getId(), studentDTO.getClassId()));
        // 为学生分配角色
        userRoleMapper.insert(createUserRole(user.getId(), 3));
        // 班级人数加1
        classMgmtMapper.incrementClassNumById(studentDTO.getClassId());
        // 初始化积分
        pointMapper.createStudentPoint(user.getId(), 0);
    }

    /**
     * 更新学生信息
     * @param updateStudentDTO 更新学生的 DTO 对象
     */
    @Override
    public void update(UpdateStudentDTO updateStudentDTO) {
        updateStudentInfo(updateStudentDTO);
        if (updateStudentDTO.getClassId() != null) {
            updateClassStudent(updateStudentDTO.getUserId(), updateStudentDTO.getClassId());
        }
    }

    /**
     * 删除学生，并更新班级人数
     * @param studentId 学生 ID
     */
    @Override
    public void deleteById(Integer studentId) {
        //删除学生积分
        pointMapper.deleteStudentPoint(studentId);
        pointRecordsMapper.deleteStudentPointRecords(studentId);
        // 更新学生状态为删除
        updateStudentStatus(studentId, 1);
        // 禁用用户
        updateUserStatus(studentId, "disable");
        // 获取该学生所属的班级 ID，并将班级人数减1
        ClassStudent classStudent = getClassStudentByStudentId(studentId);
        if (classStudent != null) {
            classMgmtMapper.decrementClassNumById(classStudent.getClassId());
        }
    }

    /**
     * 根据关键词查询学生列表
     * @param keyWord 搜索关键词
     * @return 学生信息列表
     */
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

    // 获取学生 VO 列表
    private List<StudentVO> getStudentVOSByCondition(LambdaQueryWrapper<Student> queryWrapper) {
        return getStudentVOSByCondition(queryWrapper, null);
    }

    // 根据条件和班级 ID 获取学生 VO 列表
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

    // 根据学生 ID 获取学生实体
    private Student getStudentById(Integer studentId) {
        return studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getUserId, studentId)
                .ne(Student::getStatus, 1));
    }

    // 根据用户 ID 获取用户实体
    private User getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    // 根据学生 ID 获取班级学生实体
    private ClassStudent getClassStudentByStudentId(Integer studentId) {
        return classStudentMapper.selectOne(new LambdaQueryWrapper<ClassStudent>()
                .eq(ClassStudent::getStudentId, studentId));
    }

    // 根据班级 ID 获取班级名称
    private String getClassNameById(Integer classId) {
        return classMgmtMapper.selectById(classId).getName();
    }

    // 构建学生详细信息 VO 对象
    private StudentInfoVO buildStudentInfoVO(Student student, User user, ClassStudent classStudent) {
        StudentInfoVO studentInfoVO = new StudentInfoVO();
        BeanUtils.copyProperties(student, studentInfoVO);
        studentInfoVO.setUserName(user.getNickname());
        studentInfoVO.setClassId(classStudent.getClassId());
        studentInfoVO.setMingPinClassName(getClassNameById(classStudent.getClassId()));
        return studentInfoVO;
    }

    // 创建用户实体
    private User createUser(StudentDTO studentDTO) {
        User user = new User();
        user.setBoundPhone(studentDTO.getParentPhone());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setNickname(studentDTO.getStudentName());
        user.setStatus("enable");
        userMapper.insert(user);
        return user;
    }

    // 创建学生实体
    private Student createStudent(StudentDTO studentDTO, Integer userId) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setUserId(userId);
        return student;
    }

    // 创建班级学生实体
    private ClassStudent createClassStudent(Integer studentId, Integer classId) {
        ClassStudent classStudent = new ClassStudent();
        classStudent.setStudentId(studentId);
        classStudent.setClassId(classId);
        return classStudent;
    }

    // 创建用户角色实体
    private UserRole createUserRole(Integer userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

    // 更新学生信息
    private void updateStudentInfo(UpdateStudentDTO updateStudentDTO) {
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getUserId, updateStudentDTO.getUserId()));
        BeanUtils.copyProperties(updateStudentDTO, student);
        studentMapper.updateById(student);
    }

    // 更新班级学生信息
    private void updateClassStudent(Integer studentId, Integer classId) {
        classStudentMapper.update(null, new LambdaUpdateWrapper<ClassStudent>()
                .eq(ClassStudent::getStudentId, studentId)
                .set(ClassStudent::getClassId, classId));
    }

    // 更新学生状态
    private void updateStudentStatus(Integer studentId, Integer status) {
        Student student = studentMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getUserId, studentId)
                .eq(Student::getStatus, 0));
        student.setStatus(status);
        studentMapper.updateById(student);
    }

    // 更新用户状态
    private void updateUserStatus(Integer userId, String status) {
        User user = userMapper.selectById(userId);
        user.setStatus(status);
        userMapper.updateById(user);
    }
}
