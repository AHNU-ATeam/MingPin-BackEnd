package com.chuanglian.mingpin.entity.leave;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("leaveManagement.leave")
public class Leave {
    @TableId(value = "leave_id", type = IdType.AUTO)
    private Integer leaveId; // 主键，自增

    private Integer userId; // 用户ID

    private Date date; // 日期

    private String cause; // 请假原因

    private Integer leaveStatus; // 请假状态(0-未审批 1-已审批 2-已拒绝)

    private Integer classId; // 班级ID

    private String comment; // 审批意见

    private Integer status; // 逻辑删除标识(1-已删除 0-未删除)

    private Date createdAt; // 创建时间，默认当前时间

    private Date updatedAt; // 更新时间，默认当前时间
}