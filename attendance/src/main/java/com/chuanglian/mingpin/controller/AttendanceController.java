package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.AttendanceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private ObjectMapper objectMapper; // 注入 ObjectMapper

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
    public Result getStudAttendance(@PathParam("id") Integer id,@PathParam("date")String date) throws JsonProcessingException {
        Map<Integer, List<StudentAttendance>> studAttendance = attendanceService.getStudAttendance(id, date);
        return Result.success(studAttendance);
    }
    /**
     * 签到
     */
    @PutMapping("/stuAttendance1")
    public Result stuAttendance(@PathParam("id") Integer id){
        return Result.success(attendanceService.stuAttendance(id));
    }

    @PutMapping("/stuUnattendance")
    public Result stuUnattendance(@PathParam("id") Integer id){
        return Result.success(attendanceService.stuUnattendance(id));
    }

    @PutMapping("/AskForLeave")
    public Result stuAskForLeave(@PathParam("id") Integer id){
        return Result.success(attendanceService.stuAskForLeave(id));
    }

    @PutMapping("/StuSignOut")
    public Result StuSignOut(@PathParam("id") Integer id){
        return Result.success(attendanceService.StuSignOut(id));
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
