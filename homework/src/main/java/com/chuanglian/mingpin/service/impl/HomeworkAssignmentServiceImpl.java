package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;
import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.mapper.homework.HomeworkAssignmentMapper;
import com.chuanglian.mingpin.mapper.homework.HomeworkSubmissionMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.service.HomeworkAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeworkAssignmentServiceImpl implements HomeworkAssignmentService {

    @Autowired
    private HomeworkAssignmentMapper homeworkAssignmentMapper;
    @Autowired
    private HomeworkSubmissionMapper homeworkSubmissionMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void publish(HomeworkAssignment homeworkAssignment) {

        homeworkAssignment.setCreatedAt(LocalDateTime.now());
        homeworkAssignment.setUpdatedAt(LocalDateTime.now());
        homeworkAssignmentMapper.insert(homeworkAssignment);
    }

    /**
     * 根据作业ID查找作业信息，并确保返回的作业不是已删除状态。
     * @param assignmentId 作业ID
     * @return 作业对象，如果找不到或作业已被删除，则返回null
     */
    public HomeworkAssignment findById(Integer assignmentId) {
        // 使用QueryWrapper直接排除已删除的记录
        QueryWrapper<HomeworkAssignment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("assignment_id", assignmentId)
                .ne("status", 1); // 排除status为1的记录

        return homeworkAssignmentMapper.selectOne(queryWrapper);
    }

    @Override
    public List<HomeworkAssignment> findByStudent(Integer studentUserId) {
        QueryWrapper<HomeworkAssignment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_user_id", studentUserId)
                .ne("status", 1); // 不等于 1，即未被删除的作业
        return homeworkAssignmentMapper.selectList(queryWrapper);
    }

    @Override
    public void delete(Integer assignmentId) {
        LambdaUpdateWrapper<HomeworkAssignment> homeworkAssignmentLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        homeworkAssignmentLambdaUpdateWrapper.eq(HomeworkAssignment::getAssignmentId, assignmentId);
        homeworkAssignmentLambdaUpdateWrapper.set(HomeworkAssignment::getStatus, 1);
        homeworkAssignmentMapper.update(null, homeworkAssignmentLambdaUpdateWrapper);

        //删除该作业下的所有提交
        LambdaUpdateWrapper<HomeworkSubmission> homeworkSubmissionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        homeworkSubmissionLambdaUpdateWrapper.eq(HomeworkSubmission::getAssignmentId, assignmentId);
        homeworkSubmissionLambdaUpdateWrapper.set(HomeworkSubmission::getStatus, 1);
        homeworkSubmissionMapper.update(null, homeworkSubmissionLambdaUpdateWrapper);
    }

    @Override
    public void update(HomeworkAssignment homeworkAssignment) {
        homeworkAssignment.setUpdatedAt(LocalDateTime.now());
        LambdaUpdateWrapper<HomeworkAssignment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HomeworkAssignment::getAssignmentId, homeworkAssignment.getAssignmentId());
        homeworkAssignmentMapper.update(homeworkAssignment, wrapper);
    }

}
