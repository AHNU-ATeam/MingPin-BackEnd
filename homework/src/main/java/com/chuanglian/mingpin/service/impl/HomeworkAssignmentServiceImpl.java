package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.campus.ClassStudent;
import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;
import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.entity.permission.Role;
import com.chuanglian.mingpin.entity.permission.UserRole;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.ClassStudentMapper;
import com.chuanglian.mingpin.mapper.homework.HomeworkAssignmentMapper;
import com.chuanglian.mingpin.mapper.homework.HomeworkSubmissionMapper;
import com.chuanglian.mingpin.mapper.permission.RoleMapper;
import com.chuanglian.mingpin.mapper.permission.UserRoleMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.HomeworkAssignmentService;
import com.chuanglian.mingpin.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeworkAssignmentServiceImpl implements HomeworkAssignmentService {

    private final HomeworkAssignmentMapper homeworkAssignmentMapper;
    private final HomeworkSubmissionMapper homeworkSubmissionMapper;
    private final UserMapper userMapper;
    private final StudentMapper studentMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final ClassStudentMapper classStudentMapper;

    @Autowired
    public HomeworkAssignmentServiceImpl (HomeworkAssignmentMapper homeworkAssignmentMapper,
                                          HomeworkSubmissionMapper homeworkSubmissionMapper,
                                          UserMapper userMapper, StudentMapper studentMapper,
                                          UserRoleMapper userRoleMapper, RoleMapper roleMapper,
                                          ClassStudentMapper classStudentMapper) {
        this.homeworkAssignmentMapper = homeworkAssignmentMapper;
        this.homeworkSubmissionMapper = homeworkSubmissionMapper;
        this.userMapper = userMapper;
        this.studentMapper = studentMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.classStudentMapper = classStudentMapper;
    }

    @Override
    @Transactional
    public void publish(HomeworkAssignment homeworkAssignment) {
        User user = userMapper.selectById(homeworkAssignment.getStudentUserId());
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, homeworkAssignment.getStudentUserId());
        UserRole userRole = userRoleMapper.selectOne(queryWrapper);
        Role role = roleMapper.selectById(userRole.getRoleId());
        if (role.getRole().equals(RoleEnum.STUDENT.getType())) {
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getUserId, user.getId());
            Student student = studentMapper.selectOne(studentLambdaQueryWrapper);
            LambdaQueryWrapper<ClassStudent> classStudentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            classStudentLambdaQueryWrapper.eq(ClassStudent::getStudentId, student.getStudentId());
            ClassStudent classStudent = classStudentMapper.selectOne(classStudentLambdaQueryWrapper);
            homeworkAssignment.setClassId(classStudent.getClassId());
            homeworkAssignment.setCorrectStatus(0);
            homeworkAssignment.setCreatedAt(LocalDateTime.now());
            homeworkAssignment.setUpdatedAt(LocalDateTime.now());
            homeworkAssignmentMapper.insert(homeworkAssignment);
        }
    }

    @Override
    public HomeworkAssignment findById(Integer assignmentId) {
        // 使用QueryWrapper直接排除已删除的记录
        LambdaQueryWrapper<HomeworkAssignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HomeworkAssignment::getAssignmentId, assignmentId)
                .ne(HomeworkAssignment::getStatus, 1);

        return homeworkAssignmentMapper.selectOne(queryWrapper);
    }

    @Override
    public List<HomeworkAssignment> findByStudent(Integer studentUserId) {
        LambdaQueryWrapper<HomeworkAssignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HomeworkAssignment::getStudentUserId, studentUserId)
                .ne(HomeworkAssignment::getStatus, 1); // 不等于 1，即未被删除的作业
        return homeworkAssignmentMapper.selectList(queryWrapper);
    }

    @Override
    public void delete(Integer assignmentId) {
        LambdaUpdateWrapper<HomeworkAssignment> homeworkAssignmentLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        homeworkAssignmentLambdaUpdateWrapper.eq(HomeworkAssignment::getAssignmentId, assignmentId)
                        .set(HomeworkAssignment::getStatus, 1);
        homeworkAssignmentMapper.update(null, homeworkAssignmentLambdaUpdateWrapper);

        //删除该作业下的所有提交
        LambdaUpdateWrapper<HomeworkSubmission> homeworkSubmissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        homeworkSubmissionLambdaUpdateWrapper.eq(HomeworkSubmission::getAssignmentId, assignmentId)
                        .set(HomeworkSubmission::getStatus, 1);
        homeworkSubmissionMapper.update(null, homeworkSubmissionLambdaUpdateWrapper);
    }

    @Override
    public void update(HomeworkAssignment homeworkAssignment) {
        homeworkAssignment.setUpdatedAt(LocalDateTime.now());
        LambdaUpdateWrapper<HomeworkAssignment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HomeworkAssignment::getAssignmentId, homeworkAssignment.getAssignmentId())
                        .eq(HomeworkAssignment::getStudentUserId, homeworkAssignment.getStudentUserId());
        homeworkAssignmentMapper.update(homeworkAssignment, wrapper);
    }

}
