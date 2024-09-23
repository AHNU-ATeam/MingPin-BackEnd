package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;
import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.mapper.homework.HomeworkAssignmentMapper;
import com.chuanglian.mingpin.mapper.homework.HomeworkSubmissionMapper;
import com.chuanglian.mingpin.service.HomeworkSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HomeworkSubmissionServiceImpl implements HomeworkSubmissionService {

    private final HomeworkSubmissionMapper homeworkSubmissionMapper;

    private final HomeworkAssignmentMapper homeworkAssignmentMapper;

    @Autowired
    public HomeworkSubmissionServiceImpl(HomeworkSubmissionMapper homeworkSubmissionMapper, HomeworkAssignmentMapper homeworkAssignmentMapper) {
        this.homeworkSubmissionMapper = homeworkSubmissionMapper;
        this.homeworkAssignmentMapper = homeworkAssignmentMapper;
    }

    @Override
    @Transactional
    public void submit(HomeworkSubmission homeworkSubmission) {
        HomeworkAssignment homeworkAssignment = homeworkAssignmentMapper.selectById(homeworkSubmission.getAssignmentId());
        homeworkAssignment.setCorrectStatus(1);
        homeworkSubmission.setCreatedAt(LocalDateTime.now());
        homeworkSubmission.setUpdatedAt(LocalDateTime.now());
        homeworkSubmissionMapper.insert(homeworkSubmission);
    }

    @Override
    public void delete(Integer submissionId) {
        LambdaUpdateWrapper<HomeworkSubmission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HomeworkSubmission::getSubmissionId, submissionId)
                .set(HomeworkSubmission::getStatus, 1);
        homeworkSubmissionMapper.update(null, wrapper);
    }

    @Override
    public void update(HomeworkSubmission homeworkSubmission) {
        homeworkSubmission.setUpdatedAt(LocalDateTime.now());

        LambdaUpdateWrapper<HomeworkSubmission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HomeworkSubmission::getSubmissionId, homeworkSubmission.getSubmissionId());

        homeworkSubmissionMapper.update(homeworkSubmission, wrapper);
    }

    @Override
    public HomeworkSubmission selectById(Integer assignmentId) {
        LambdaQueryWrapper<HomeworkSubmission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HomeworkSubmission::getAssignmentId, assignmentId)
                .ne(HomeworkSubmission::getStatus, 1);
        return homeworkSubmissionMapper.selectOne(queryWrapper);
    }
}
