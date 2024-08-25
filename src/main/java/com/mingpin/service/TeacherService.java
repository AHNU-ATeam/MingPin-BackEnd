package com.mingpin.service;

import com.mingpin.pojo.Teacher;

import java.util.List;

public interface TeacherService {
     void add(Teacher teacher) ;

    List<Teacher> list();

    void delete(Integer id);

    void update(Teacher teacher);

    Teacher getTeacherById(Long teacherId);
}
