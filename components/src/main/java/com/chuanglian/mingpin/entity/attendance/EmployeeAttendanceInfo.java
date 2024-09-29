package com.chuanglian.mingpin.entity.attendance;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

/*
员工的签到
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("[attendanceManagement].[employee_attendance_info]")
public class EmployeeAttendanceInfo {

    @TableId(value = "attendance_id", type = IdType.AUTO)
    private int attendanceId;  // 主键ID

    private int campusId;  // 校区ID

    private String location;  // 签到位置

    private double centerLatitude; // 中心点纬度

    private double centerLongitude; // 中心点经度

    private Integer radius; // 打卡半径

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;  // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;  // 更新时间
}