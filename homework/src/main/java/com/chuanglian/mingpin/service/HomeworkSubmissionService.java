package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;
import com.chuanglian.mingpin.entity.homework.vo.CorrectSubmissionVo;

import java.util.List;

public interface HomeworkSubmissionService {
    void submit(HomeworkSubmission homeworkSubmission);

    void delete(Integer submissionId);

    void update(HomeworkSubmission homeworkSubmission);

    HomeworkSubmission selectById(Integer submissionId);

    List<HomeworkSubmission> selectByStudent(Integer studentId);

    List<HomeworkSubmission> selectBySubmission(Integer assignmentId);

    void correct(CorrectSubmissionVo correctSubmissionVo);

    List<HomeworkSubmission> selectBySubmitStatus(Integer assignmentId, Integer submitStatus);
}
