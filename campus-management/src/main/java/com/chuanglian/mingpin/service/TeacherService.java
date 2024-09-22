package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.pojo.TeacherVO;
import com.chuanglian.mingpin.pojo.Result;

public interface TeacherService {
    Result add(TeacherVO teacher) ;

    Result list();

    Result delete(Integer teacherId);

    Result update(TeacherVO teacherVO);

    Result getTeacherById(Integer teacherId);
}
