package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.entity.homework.vo.CorrectSubmissionVo;
import com.chuanglian.mingpin.entity.homework.vo.SubmissionDetailVo;

import java.util.List;

public interface HomeworkSubmissionService {
    void submit(HomeworkSubmission homeworkSubmission);

    void delete(Integer submissionId);

    void update(HomeworkSubmission homeworkSubmission);

    HomeworkSubmission selectById(Integer submissionId);

    List<SubmissionDetailVo> selectByStudent(Integer studentId);

    List<SubmissionDetailVo> selectBySubmission(Integer assignmentId);

    void correct(CorrectSubmissionVo correctSubmissionVo);

    List<SubmissionDetailVo> selectBySubmitStatus(Integer assignmentId, Integer submitStatus);
}
