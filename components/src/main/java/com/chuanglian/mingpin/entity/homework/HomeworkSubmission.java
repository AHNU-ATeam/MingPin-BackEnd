package com.chuanglian.mingpin.entity.homework;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("[homeworkManagement].[homework_submission]")
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkSubmission {
    private Integer submissionId;     // 提交唯一标识
    private Integer assignmentId;     // 对应的作业任务ID
    private Integer studentId;        // 学生ID
    private String textContent;       // 学生提交内容
    private LocalDateTime submissionDate;      // 学生提交日期
    private BigDecimal score;         // 作业成绩
    private String comments;          // 教师评语
    private Integer submitStatus;     // 提交状态 (0 表示未提交, 1 表示已提交, 2 表示已批改)
    private LocalDateTime createdAt;           // 提交创建时间
    private LocalDateTime updatedAt;           // 提交更新时间
    private Integer status;           // 删除状态 (0 表示未删除, 1 表示已删除)
}
