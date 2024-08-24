package org.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
import com.chuanglian.mingpin.entity.attendance.StudentAttendanceInfo;
import com.chuanglian.mingpin.pojo.Result;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.chuanglian.mingpin.service.AttendanceInfoService;
import org.chuanglian.mingpin.service.impl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
//@RequestMapping("/AttendanceInfo")
public class AttendanceInfoController {
    @Autowired
    private AttendanceInfoService attendanceInfoService;

    @GetMapping  ("/emp1")
//    @PreAuthorize("hasAuthority('sys:attendance:clock_in')")
    public Result getEmpInfo(@PathParam("id") Integer id){
        return Result.success(attendanceInfoService.getEmpInfo(id));
    }

    @PutMapping("/emp")
    public Result updateEmpInfo(@RequestBody EmployeeAttendanceInfo employeeAttendanceInfo){
        return Result.success(attendanceInfoService.updateEmpInfo(employeeAttendanceInfo));
    }

    @GetMapping("/stu/{id}")
    public Result getStuInfo(@PathVariable Integer id){
        return Result.success(attendanceInfoService.getStuInfo(id));
    }

    @PutMapping("/stu")
    public Result updateStuInfo(@RequestBody StudentAttendanceInfo studentAttendanceInfo){
        return Result.success(attendanceInfoService.updateStuInfo(studentAttendanceInfo));
    }
}
