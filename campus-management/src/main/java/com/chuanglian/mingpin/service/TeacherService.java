package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.vo.TeacherVO;
import com.chuanglian.mingpin.pojo.Result;

import java.util.List;

public interface TeacherService {
    Result add(TeacherVO teacher) ;

    Result list();

    Result delete(Integer teacherId);

    Result update(TeacherVO teacherVO);

    Result getTeacherById(Integer teacherId);
}
