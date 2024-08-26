package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.AttendanceService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 根据校区id查找班级人数与请假人数
     * @param id   校区id
     * @return
     */
    @GetMapping("/ClassAttendance")
    public Result getClassAttendance(@PathParam("id") Integer id,@PathParam("date")String date){
        return Result.success(attendanceService.getClassAttendance(id,date));
    }

    /**
     *根据班级id与日期查询签到信息
     * @param id 班级id
     * @param date 日期
     * @return
     */
    @GetMapping("/stuAttendance")
    public Result getStudAttendance(@PathParam("id") Integer id,@PathParam("date")String date){
        return Result.success(attendanceService.getStudAttendance(id,date));
    }
    /**
     * 签到
     */
    @PutMapping("/stuAttendance1")
    public Result stuAttendance(@RequestBody StudentAttendance studentAttendance){
        return Result.success(attendanceService.stuAttendance(studentAttendance));
    }

    @PutMapping("/stuUnattendance")
    public Result stuUnattendance(@RequestBody StudentAttendance studentAttendance){
        return Result.success(attendanceService.stuUnattendance(studentAttendance));
    }

    @PutMapping("/AskForLeave")
    public Result stuAskForLeave(@RequestBody StudentAttendance studentAttendance){
        return Result.success(attendanceService.stuAskForLeave(studentAttendance));
    }

    @PutMapping("/StuSignOut")
    public Result StuSignOut(@RequestBody StudentAttendance studentAttendance){
        return Result.success(attendanceService.StuSignOut(studentAttendance));
    }

    /**
     * 根据校区id，查询所有教师签到信息
     * @param id
     * @param date
     * @return
     */
    @GetMapping("teaAttendance")
    public Result getTeaAttendance(@PathParam("id") Integer id,@PathParam("date")String date){
        return Result.success(attendanceService.getTeaAttendance(id,date));
    }

    /**
     * 教师打卡，根据type
     * @param teacher_id 教师id
     * @param type 打卡类型
     * @return
     */
    @PutMapping("teaAttendance")
    public Result setTeaAttendance(@PathParam("teacher_id") Integer teacher_id,
                                   @PathParam("type") Integer type,
                                   @PathParam("location")String location,
                                   @PathParam("photo") String photo){
        System.out.println(teacher_id+"sdfghjk");
        System.out.println(type);
        Integer re = attendanceService.setTeaAttendance(teacher_id,type,location,photo);
        System.out.println(re);
        return Result.success(re);
    }

}
