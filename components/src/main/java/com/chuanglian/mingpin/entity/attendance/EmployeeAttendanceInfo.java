//package com.chuanglian.mingpin.entity.attendance;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
///*
//员工的签到
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@TableName("[attendanceManagement].[employee_attendance_info]")
//public class EmployeeAttendanceInfo {
//
//    @JsonProperty("attendance_id")
//    @TableId(value = "attendance_id", type = IdType.AUTO)
//    private int attendanceId;  // 主键ID
//
//    @JsonProperty("start_time")
//    private LocalTime startTime;  // 第一次上班时间开始
//
//    @JsonProperty("end_time")
//    private LocalTime endTime;  // 第一次上班时间结束
//
//    @JsonProperty("start_time_2")
//    private LocalTime startTime2;  // 第一次下班时间开始
//
//    @JsonProperty("end_time_2")
//    private LocalTime endTime2;  // 第一次下班时间结束
//
//    @JsonProperty("start_time_3")
//    private LocalTime startTime3;  // 第二次上班时间开始
//
//    @JsonProperty("end_time_3")
//    private LocalTime endTime3;  // 第二次上班时间结束
//
//    @JsonProperty("start_time_4")
//    private LocalTime startTime4;  // 第二次下班时间开始
//
//    @JsonProperty("end_time_4")
//    private LocalTime endTime4;  // 第二次下班时间结束
//
//    @JsonProperty("campus_id")
//    private int campusId;  // 校区ID
//
//    /**
//     * 签到类型
//     * 0: 只需打卡一次上下班
//     * 1: 需要打卡两次上下班
//     */
//    private int type;
//
//    @JsonProperty("created_at")
//    private LocalDateTime createdAt;  // 创建时间
//
//    @JsonProperty("updated_at")
//    private LocalDateTime updatedAt;  // 更新时间
//}