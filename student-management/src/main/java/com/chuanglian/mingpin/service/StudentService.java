package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.user.Student;

import java.util.List;

public interface StudentService {
    List<Student> campusList(Integer campusId);

    List<Student> classList(Integer campusId);

    Student findById(Integer studentId);

    void add(Student student);

    void update(Student student);

    void deleteById(Integer studentId);
}
