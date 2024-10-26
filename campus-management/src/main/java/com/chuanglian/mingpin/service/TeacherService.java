package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.pojo.*;

import java.util.List;

public interface TeacherService {
    Result add(CampusTeacherVO teacher) ;

    List<TeacherVoForShow> getAllTeacherUsers(Integer campusId);

    Result delete(Integer teacherId);

    Result update(TeacherVoForUpdate teacherVoForUpdate);

    Result getTeacherById(Integer teacherId);
}
