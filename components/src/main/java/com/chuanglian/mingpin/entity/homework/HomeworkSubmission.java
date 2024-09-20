package com.chuanglian.mingpin.entity.homework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "submission_id", type = IdType.AUTO)
    private Integer submissionId;     // 提交唯一标识
    private Integer assignmentId;     // 对应的作业任务ID
    private Integer teacherUserId;        // 教师ID
    private String textContent;       // 教师提交内容
    private Integer submitStatus;     // 提交状态 (0 表示未提交, 1 表示已提交, 2 表示已批改)
    private LocalDateTime createdAt;           // 提交创建时间
    private LocalDateTime updatedAt;           // 提交更新时间
    private Integer status;           // 删除状态 (0 表示未删除, 1 表示已删除)
    private Integer studentUserId;      //学生id
}
