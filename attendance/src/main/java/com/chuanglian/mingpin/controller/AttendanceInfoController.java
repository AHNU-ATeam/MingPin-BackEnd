package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendanceInfo;
//import com.chuanglian.mingpin.entity.attendance.StudentAttendanceInfo;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.AttendanceInfoService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/AttendanceInfo")
public class AttendanceInfoController {

    @Autowired
    private AttendanceInfoService attendanceInfoService;

    @GetMapping  ("/emp/get")
//    @PreAuthorize("hasAuthority('sys:attendance:clock_in')")
    public Result getEmpInfo(@PathParam("id") Integer id){
//    public Result getEmpInfo(){
        return Result.success(attendanceInfoService.getEmpInfo(id));
    }

    @PostMapping("/emp/update")
    public Result updateEmpInfo(@RequestBody EmployeeAttendanceInfo employeeAttendanceInfo){
        return Result.success(attendanceInfoService.updateEmpInfo(employeeAttendanceInfo));
    }
}
