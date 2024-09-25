package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;


public interface HomeworkSubmissionService {
    void submit(HomeworkSubmission homeworkSubmission);

    void delete(Integer submissionId);

    void update(HomeworkSubmission homeworkSubmission);

    HomeworkSubmission selectById(Integer assignmentId);

}
