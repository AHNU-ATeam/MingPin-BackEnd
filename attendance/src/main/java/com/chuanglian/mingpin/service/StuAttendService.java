package com.chuanglian.mingpin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.vo.StudentAttendanceVo;

import java.util.List;

public interface StuAttendService extends IService<StudentAttendance> {

    Integer stuAttendance(Integer id, String photo);

    Integer stuCheckOut(Integer id);

    List<StudentAttendanceVo> selectStuAttendance(Integer id);

    List<StudentAttendanceVo> selectAllStuAttendance(Integer id);

    List<StudentAttendanceVo> selectClassAllStuAttend(Integer id);

    List<StudentAttendanceVo> selectClassTodayAttend(Integer id);
}
