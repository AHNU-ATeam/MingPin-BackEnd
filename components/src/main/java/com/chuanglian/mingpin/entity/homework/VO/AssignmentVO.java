package com.chuanglian.mingpin.entity.homework.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentVO {
    private Integer assignmentId; // 主键
    private String title;         // 作业标题
    private String description;   // 作业描述
    private Integer studentUserId;    // 学生的用户ID
    private Integer classId;      // 班级ID
    private Integer correctStatus;      //发布状态
}
