package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;

import java.util.List;

public interface HomeworkAssignmentService {
    void publish(HomeworkAssignment homeworkAssignment);

    HomeworkAssignment findById(Integer assignmentId);

    List<HomeworkAssignment> findByStudent(Integer studentUserId);

    void delete(Integer assignmentId);

    void update(HomeworkAssignment homeworkAssignment);

}
