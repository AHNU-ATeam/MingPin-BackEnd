package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalVO extends UserVO {
    @JsonProperty("campus")
    private List<Integer> campusId;
    @JsonProperty("class")
    private List<Integer> classId;
    @JsonProperty("balance")
    private BigDecimal balance;
    @JsonProperty("teacher_num")
    private Long teacherNum;
    @JsonProperty("student_num")
    private Long studentNum;
}
