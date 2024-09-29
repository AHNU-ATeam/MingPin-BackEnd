package com.chuanglian.mingpin.service;


import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;

public interface AttendanceInfoService {
    EmployeeAttendanceInfo getEmpInfo(Integer id);

    int updateEmpInfo(EmployeeAttendanceInfo employeeAttendanceInfo);

}
