package com.chuanglian.mingpin.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendanceVo {
    private Integer id;  // 主键ID

    private Integer employeeId;  // 员工ID

    private LocalDate date;  // 签到日期

    private LocalTime time;  // 签到时间

    private LocalTime checkOutTime;//签退时间

    private String location;  // 签到位置

    private Double centerLatitude; // 中心点纬度

    private Double centerLongitude; // 中心点经度

    private String photo;  // 照片链接

    private LocalDateTime createdAt;

    private Integer type; //签到类型,默认为0没签到,1为签到，2为签退

    private String name; //员工的姓名
}
