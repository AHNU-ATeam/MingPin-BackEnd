package com.chuanglian.mingpin.entity.homework.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {
    private String title;         // 作业标题
    private String description;   // 作业描述
    private Integer studentUserId;    // 学生的用户ID
}
