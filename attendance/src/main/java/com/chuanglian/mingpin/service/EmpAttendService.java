package com.chuanglian.mingpin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.vo.EmployeeAttendanceVo;

import java.util.List;

public interface EmpAttendService extends IService<EmployeeAttendance> {
    int empAttendance(EmployeeAttendance employeeAttendance);

    int empCheckOut(Integer id);

    List<EmployeeAttendanceVo> selectEmpAttendance(Integer id);

    List<EmployeeAttendanceVo> selectAllEmpAttendance(Integer id);
}
