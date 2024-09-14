package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;

import java.util.List;

public interface HomeworkAssignmentService {
    void publish(HomeworkAssignment homeworkAssignment);

    HomeworkAssignment findById(Integer assignmentId);

    List<HomeworkAssignment> findByClass(Integer classId);

    List<HomeworkAssignment> findByTeacher(Integer teacherId);

    void delete(Integer assignmentId);

    void update(HomeworkAssignment homeworkAssignment);
}
