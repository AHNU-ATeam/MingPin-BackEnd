package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.entity.user.Teacher;

import java.util.List;

public interface TeacherService {
     void add(Teacher teacher) ;

    List<Teacher> list();

    void delete(Integer id);

    void update(Teacher teacher);

    Teacher getTeacherById(Long teacherId);
}
