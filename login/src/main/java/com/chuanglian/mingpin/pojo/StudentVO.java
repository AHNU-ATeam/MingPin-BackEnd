package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO extends UserVO {
    @JsonProperty("class_id")
    private Integer classId;
    @JsonProperty("class_name")
    private String className;
    @JsonProperty("campus_id")
    private Integer campusId;
    @JsonProperty("campus_name")
    private String campusName;
    private Integer point;
}
