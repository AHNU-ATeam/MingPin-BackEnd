//package com.chuanglian.mingpin.entity.attendance;
//
//import com.baomidou.mybatisplus.annotation.*;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//
///*
//学生的签到
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@TableName("[attendanceManagement].[student_attendance_info]")
//public class StudentAttendanceInfo
//{
//    @JsonProperty("attendance_id")
//    @TableId(value = "attendance_id",type = IdType.AUTO)
//    private int attendanceId;  // 主键ID
//    @JsonProperty("start_time")
//    private LocalTime startTime;  // 起始时间
//
//    @JsonProperty("campus_id")
//    private int campusId;  // 校区ID
//    @TableField(fill = FieldFill.INSERT)
//    private LocalDateTime createdAt;
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    private LocalDateTime updatedAt;
//
//}