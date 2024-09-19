package com.chuanglian.mingpin.entity.homework.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDetailVo {
    private Integer submissionId;     // 提交唯一标识
    private Integer assignmentId;     // 对应的作业任务ID
    private Integer studentId;        // 学生ID
    private String textContent;       // 学生提交内容
    private LocalDateTime submissionDate;      // 学生提交日期
    private BigDecimal score;         // 作业成绩
    private String comments;          // 教师评语
    private Integer submitStatus;
    private Integer status;
    private String studentName;
}
