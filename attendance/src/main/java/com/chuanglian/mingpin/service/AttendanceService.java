package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

    List<Map<String, Object>> getClassAttendance(Integer id,String date);

    Map<Integer, List<StudentAttendance>> getStudAttendance(Integer id, String date);


    Integer stuAttendance(Integer studentAttendance);

    Integer stuUnattendance(Integer studentAttendance);

    Integer stuAskForLeave(Integer studentAttendance);

    Integer StuSignOut(Integer studentAttendance);

    List<EmployeeAttendance> getTeaAttendance(Integer id, String date);


    Integer setTeaAttendance(Integer id, Integer type, String location, String photo);
}
