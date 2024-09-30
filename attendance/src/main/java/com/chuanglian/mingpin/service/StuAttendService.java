package com.chuanglian.mingpin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;

import java.util.List;

public interface StuAttendService extends IService<StudentAttendance> {

    Integer stuAttendance(Integer id, String photo);

    Integer stuCheckOut(Integer id);

    List<StudentAttendance> selectStuAttendance(Integer id);

    List<StudentAttendance> selectAllStuAttendance(Integer id);
}
