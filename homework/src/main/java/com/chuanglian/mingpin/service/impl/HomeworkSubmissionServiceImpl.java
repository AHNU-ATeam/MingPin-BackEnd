package com.chuanglian.mingpin.service.impl;

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;
import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.entity.homework.vo.CorrectSubmissionVo;
import com.chuanglian.mingpin.mapper.homework.HomeworkAssignmentMapper;
import com.chuanglian.mingpin.mapper.homework.HomeworkSubmissionMapper;
import com.chuanglian.mingpin.service.HomeworkSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeworkSubmissionServiceImpl implements HomeworkSubmissionService {

    @Autowired
    private HomeworkSubmissionMapper homeworkSubmissionMapper;
    @Autowired
    private HomeworkAssignmentMapper homeworkAssignmentMapper;

    @Override
    public void submit(HomeworkSubmission homeworkSubmission) {
        homeworkSubmission.setCreatedAt(LocalDateTime.now());
        homeworkSubmission.setUpdatedAt(LocalDateTime.now());
        homeworkSubmission.setSubmitStatus(1);
        homeworkSubmissionMapper.insert(homeworkSubmission);
    }

    @Override
    public void delete(Integer submissionId) {
        LambdaUpdateWrapper<HomeworkSubmission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HomeworkSubmission::getSubmissionId, submissionId);
        wrapper.set(HomeworkSubmission::getStatus, 1);
        homeworkSubmissionMapper.update(null, wrapper);
    }

    @Override
    public void update(HomeworkSubmission homeworkSubmission) {
        homeworkSubmission.setUpdatedAt(LocalDateTime.now());
        LambdaUpdateWrapper<HomeworkSubmission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(HomeworkSubmission::getAssignmentId, homeworkSubmission.getAssignmentId());
        homeworkSubmissionMapper.update(homeworkSubmission, wrapper);
    }

    @Override
    public HomeworkSubmission selectById(Integer submissionId) {
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("submission_id", submissionId)
                .ne("status", 1);
        return homeworkSubmissionMapper.selectOne(queryWrapper);
    }

    @Override
    public List<HomeworkSubmission> selectByStudent(Integer studentId) {
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId)
                .ne("status", 1);
        return homeworkSubmissionMapper.selectList(queryWrapper);
    }

    @Override
    public List<HomeworkSubmission> selectBySubmission(Integer assignmentId) {
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("assignment_id", assignmentId)
                .ne("status", 1);
        return homeworkSubmissionMapper.selectList(queryWrapper);
    }

    @Override
    public void correct(CorrectSubmissionVo correctSubmissionVo) {
        HomeworkSubmission homeworkSubmission = homeworkSubmissionMapper.selectById(correctSubmissionVo.getSubmissionId());
        Integer assignmentId = homeworkSubmission.getAssignmentId();
        HomeworkAssignment homeworkAssignment = homeworkAssignmentMapper.selectById(assignmentId);
        Integer teacherId = homeworkAssignment.getTeacherId();
        if (!correctSubmissionVo.getTeacherId().equals(teacherId)) {
            throw new ServiceException("您不能批改此作业");
        }
        homeworkSubmission.setScore(correctSubmissionVo.getScore());
        homeworkSubmission.setComments(correctSubmissionVo.getComment());
        homeworkSubmissionMapper.updateById(homeworkSubmission);
    }

    @Override
    public List<HomeworkSubmission> selectBySubmitStatus(Integer assignmentId, Integer submitStatus) {
        QueryWrapper<HomeworkSubmission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("assignment_id", assignmentId)
                .eq("submit_status", submitStatus);
        return homeworkSubmissionMapper.selectList(queryWrapper);
    }
}
