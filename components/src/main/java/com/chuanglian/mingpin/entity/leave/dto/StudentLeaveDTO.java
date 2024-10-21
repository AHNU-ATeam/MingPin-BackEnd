package com.chuanglian.mingpin.entity.leave.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentLeaveDTO {
    private Integer leaveId; // 主键，自增

    private Integer userId; // 用户ID

    private Date date; // 日期

    private String cause; // 请假原因

    private Integer classId; // 班级ID

    private Date createdAt; // 创建时间，默认当前时间
}
