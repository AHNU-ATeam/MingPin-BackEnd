package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.Recipe;
import com.chuanglian.mingpin.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> allList(Integer campusId);

    Student findById(Integer studentId);

    void add(Student student);

    void update(Student student);

    void deleteById(Integer studentId);
}
