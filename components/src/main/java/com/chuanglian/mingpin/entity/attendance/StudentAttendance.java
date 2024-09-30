package com.chuanglian.mingpin.entity.attendance;

import com.baomidou.mybatisplus.annotation.*;
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
    private Integer id;  // 主键ID

    private Integer studentId;  // 学生ID

    private LocalDate date;  // 签到日期

    private LocalTime time;  // 签到时间

    private LocalTime checkOutTime;//签退时间

    private String photo;  // 照片链接

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    private Integer type; //签到类型,默认为0没签到,1为签到，2为签退
}
