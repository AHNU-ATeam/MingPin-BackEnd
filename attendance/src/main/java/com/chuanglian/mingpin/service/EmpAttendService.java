package com.chuanglian.mingpin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chuanglian.mingpin.entity.attendance.EmpAttendDownload;
import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.entity.vo.EmployeeAttendanceVo;

import java.time.LocalDate;
import java.util.List;

public interface EmpAttendService extends IService<EmployeeAttendance> {
    int empAttendance(EmployeeAttendance employeeAttendance);

    int empCheckOut(Integer id);

    List<EmployeeAttendanceVo> selectEmpAttendance(Integer id);

    List<EmployeeAttendanceVo> selectAllEmpAttendance(Integer id);

    List<EmpAttendDownload> downloadAllEmpAttend(Integer campusId, String name, LocalDate startDate, LocalDate endDate);

    List<Integer> getEmployeeIsAttended(LocalDate date);
}
