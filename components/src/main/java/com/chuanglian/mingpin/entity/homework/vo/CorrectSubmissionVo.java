package com.chuanglian.mingpin.entity.homework.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrectSubmissionVo {
    private Integer submissionId;
    private Integer teacherId;
    private BigDecimal score;
    private String comment;
}
