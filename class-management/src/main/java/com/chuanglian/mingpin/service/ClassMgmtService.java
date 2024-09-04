package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.Teacher;

import java.util.List;

public interface ClassMgmtService {

    List<Class> list(Integer id);

    int add(Class cls);

    Integer delect(Integer id);

    Teacher getHeadTeacher(Integer headTeacherId);

    List<Student> getStudents(Integer id);

    List<Teacher> getAssistants(Class cls);

    int changeClass(Class cls);

    Class selectClass(Integer id);

    List<Teacher> getTeachers(Integer id);
}
