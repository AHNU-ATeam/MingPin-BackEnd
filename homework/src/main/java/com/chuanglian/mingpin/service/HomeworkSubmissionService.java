package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.DTO.SubmissionDTO;
import com.chuanglian.mingpin.entity.homework.DTO.UpdateSubmissionDTO;
import com.chuanglian.mingpin.entity.homework.VO.SubmissionVO;


public interface HomeworkSubmissionService {
    void submit(SubmissionDTO submissionDTO);

    void delete(Integer submissionId);

    void update(UpdateSubmissionDTO updateSubmissionDTO);

    SubmissionVO selectById(Integer assignmentId);

}
