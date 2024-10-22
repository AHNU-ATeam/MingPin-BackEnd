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
public class TeacherVO extends UserVO {
    @JsonProperty("class_id")
    Integer classId;
    @JsonProperty("class_name")
    String className;
    @JsonProperty("campus_id")
    Integer campusId;
    @JsonProperty("campus_name")
    String campusName;
}
