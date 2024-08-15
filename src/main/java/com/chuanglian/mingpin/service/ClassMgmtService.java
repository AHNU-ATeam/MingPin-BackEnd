package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.entity.Class;
import com.chuanglian.mingpin.entity.Student;
import com.chuanglian.mingpin.entity.Teacher;

import java.util.List;

public interface ClassMgmtService {

    List<Class> list(Integer id);

    int add(Class cls);

    Integer delect(Integer id);

    Teacher getHeadTeacher(Integer headTeacherId);

    List<Student> getStudents(Integer id);

    List<Teacher> getAssistants(Class cls);
}
