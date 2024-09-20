package com.chuanglian.mingpin.entity.homework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("[homeworkManagement].[homework_assignment]")
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkAssignment {
    @TableId(value = "assignment_id", type = IdType.AUTO)
    private Integer assignmentId; // 主键
    private String title;         // 作业标题
    private String description;   // 作业描述
    private Integer studentUserId;    // 学生ID
    private Integer classId;      // 班级ID
    private LocalDateTime createdAt;       // 作业创建时间
    private LocalDateTime updatedAt;       // 作业更新时间
    private Integer status;       // 删除状态 (0 表示未删除, 1 表示已删除)
}
