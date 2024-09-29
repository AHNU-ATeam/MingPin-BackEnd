package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.DTO.AssignmentDTO;
import com.chuanglian.mingpin.entity.homework.DTO.UpdateAssignmentDTO;
import com.chuanglian.mingpin.entity.homework.VO.AssignmentVO;

import java.util.List;

public interface HomeworkAssignmentService {
    void publish(AssignmentDTO assignmentDTO);

    AssignmentVO findById(Integer assignmentId);

    List<AssignmentVO> findByStudent(Integer studentUserId);

    void delete(Integer assignmentId);

    void update(UpdateAssignmentDTO updateAssignmentDTO);

}
