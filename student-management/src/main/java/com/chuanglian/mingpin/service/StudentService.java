package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    List<Student> campusList(Integer campusId);

    List<Student> classList(Integer campusId);

    Student findById(Integer studentId);

    void add(StudentDTO studentDTO);

    void update(Student student);

    void deleteById(Integer studentId);
}
