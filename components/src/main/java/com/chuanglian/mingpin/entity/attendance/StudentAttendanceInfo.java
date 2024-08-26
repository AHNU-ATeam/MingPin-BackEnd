package com.chuanglian.mingpin.entity.attendance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

/*
学生的签到
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("[attendanceManagement].[student_attendance_info]")
public class StudentAttendanceInfo
{
    @JsonProperty("attendance_id")
    @TableId(value = "attendance_id",type = IdType.AUTO)
    private int attendanceId;  // 主键ID
    @JsonProperty("start_time")
    private LocalTime startTime;  // 起始时间
    @JsonProperty("end_time")
    private LocalTime endTime;  // 截至时间
    @JsonProperty("campus_id")
    private int campusTd;  // 校区ID
    private int type;  // 签到类型 (1: 早, 2: 中, 3: 晚)
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}