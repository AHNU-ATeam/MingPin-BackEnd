package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.attendance.EmployeeAttendance;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.EmpAttendService;
import com.chuanglian.mingpin.service.StuAttendService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    StuAttendService stuAttendService;
    @Autowired
    EmpAttendService empAttendService;

    /**
     * 学生打卡
     * @param id
     * @param photo
     * @return
     */
//    @PostMapping("/stu")
//    public Result stuAttendance(@RequestParam("id") Integer id,
//                                @RequestParam("photo")String photo){
//        return Result.success(stuAttendService.stuAttendance(id,photo));
//    }
    @PostMapping("/stu")
    public Result stuAttendance(@RequestBody Map<String, Object> requestBody) {
        Integer id = (Integer) requestBody.get("id");
        String photo = (String) requestBody.get("photo");
        return Result.success(stuAttendService.stuAttendance(id, photo));
    }


    /**
     * 学生签退
     * @param id
     * @return
     */
    @PostMapping("/stu/out")
    public Result stuCheckOut(@RequestParam("id") Integer id){
        return Result.success(stuAttendService.stuCheckOut(id));
    }

    /**
     * 根据id查询学生打卡信息
     * @param id
     * @return
     */
    @GetMapping("/stu/{id}")
    public Result selectStuAttendance(@PathVariable Integer id){
        return Result.success(stuAttendService.selectStuAttendance(id));
    }

    @GetMapping("/all/stu/{id}")
    public Result selectAllStuAttendance(@PathVariable Integer id){
        return Result.success(stuAttendService.selectAllStuAttendance(id));
    }


    /**
     * 员工签到
     * @param employeeAttendance
     * @return
     */
    @PostMapping("/emp")
    public Result empAttendance(@RequestBody EmployeeAttendance employeeAttendance){
        return Result.success(empAttendService.empAttendance(employeeAttendance));
    }

    /**
     * 员工签退
     * @param id
     * @return
     */
    @PostMapping("emp/out")
    public Result empCheckOut(@RequestParam("id") Integer id){
        return Result.success(empAttendService.empCheckOut(id));
    }

    @GetMapping("/emp/{id}")
    public Result selectEmpAttendance(@PathVariable Integer id){
        return Result.success(empAttendService.selectEmpAttendance(id));
    }

    @GetMapping("/all/emp/{id}")
    public Result selectAllEmpAttendance(@PathVariable Integer id){
        return Result.success(empAttendService.selectAllEmpAttendance(id));
    }
}
