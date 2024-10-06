package com.chuanglian.mingpin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;

import java.util.List;

public interface EmpAttendService extends IService<EmployeeAttendance> {
    int empAttendance(EmployeeAttendance employeeAttendance);

    int empCheckOut(Integer id);

    List<EmployeeAttendance> selectEmpAttendance(Integer id);

    List<EmployeeAttendance> selectAllEmpAttendance(Integer id);
}
