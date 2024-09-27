package com.chuanglian.mingpin.entity.attendance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @JsonProperty("class_id")
    private int classId;
    private LocalDate date;  // 签到日期
    private LocalTime time;  // 签到时间
    private String photo;  // 照片链接
    private LocalDateTime createdAt;
    private int type;  // 打卡状态
}
