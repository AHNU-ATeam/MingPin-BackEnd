package com.chuanglian.mingpin.entity.homework.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionVO {
    private Integer submissionId;     // 提交唯一标识
    private Integer assignmentId;     // 对应的作业任务ID
    private Integer teacherUserId;        // 教师的用户ID
    private String textContent;       // 教师提交内容
    private String comments;        //教师评语
}
