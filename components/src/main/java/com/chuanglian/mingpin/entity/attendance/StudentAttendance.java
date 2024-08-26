package com.chuanglian.mingpin.entity.attendance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chuanglian.mingpin.entity.user.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
用于学生每次签到的记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("[attendanceManagement].[student_attendance]")
public class StudentAttendance
{
    @TableId(value = "id",type = IdType.AUTO)
    private int id;  // 主键ID

    @JsonProperty("student_id")
    private int studentId;  // 学生ID
    @JsonProperty("attendance_id")
    private int attendanceId;  // 签到信息ID
    @JsonProperty("class_id")
    private int classId;
    @JsonProperty("sign_out")
    private int signOut;
    private LocalDate date;  // 签到日期
    private LocalTime time;  // 签到时间
    private String location;  // 签到位置
    private int type;  // 是否按时签到，"是" 或 "否"
    private String photo;  // 照片链接
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Student student;
}