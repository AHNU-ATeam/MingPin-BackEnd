package com.chuanglian.mingpin.entity.leave.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveVO {
    private Integer leaveId; // 主键，自增

    private Integer userId; // 用户ID
    private String userName; // 用户名

    private Date date; // 日期

    private String cause; // 请假原因

    private Integer leaveStatus; // 请假状态(0-未审批 1-已审批 2-已拒绝)

    private Integer classId; // 班级ID
    private String className; // 班级名
    private Integer teacherId; // 老师ID
    private String teacherName; // 老师名

    private String comment; // 审批意见

    private Date createdAt; // 创建时间，默认当前时间
}
