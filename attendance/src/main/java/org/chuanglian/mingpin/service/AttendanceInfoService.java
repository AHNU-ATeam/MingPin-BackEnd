package org.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
import com.chuanglian.mingpin.entity.attendance.StudentAttendanceInfo;

import java.util.List;

public interface AttendanceInfoService {
    List<EmployeeAttendanceInfo> getEmpInfo(Integer id);

    int updateEmpInfo(EmployeeAttendanceInfo employeeAttendanceInfo);

    List<StudentAttendanceInfo> getStuInfo(Integer id);

    int updateStuInfo(StudentAttendanceInfo studentAttendanceInfo);
}
