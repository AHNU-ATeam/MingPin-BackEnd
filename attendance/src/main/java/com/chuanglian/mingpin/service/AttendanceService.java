package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

    List<Map<String, Object>> getClassAttendance(Integer id,String date);

    Map<Integer, List<StudentAttendance>> getStudAttendance(Integer id, String date);


    Integer stuAttendance(StudentAttendance studentAttendance);

    Integer stuUnattendance(StudentAttendance studentAttendance);

    Integer stuAskForLeave(StudentAttendance studentAttendance);

    Integer StuSignOut(StudentAttendance studentAttendance);

    List<EmployeeAttendance> getTeaAttendance(Integer id, String date);


    Integer setTeaAttendance(Integer id, Integer type, String location, String photo);
}
