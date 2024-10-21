package com.chuanglian.mingpin.entity.leave.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherLeaveDTO {
    private Integer leaveId; // 主键，自增

    private Integer userId; // 用户ID

    private Date date; // 日期

    private String cause; // 请假原因

    private Integer leaveStatus; // 请假状态(0-未审批 1-已审批 2-已拒绝)

    private Integer classId; // 班级ID

    private String comment; // 审批意见

    private Date createdAt; // 创建时间，默认当前时间
}
