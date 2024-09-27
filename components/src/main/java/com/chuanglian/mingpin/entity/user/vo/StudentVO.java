package com.chuanglian.mingpin.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {
    private Integer userId;
    private String userName;
    private String gender;
    private Integer classId;
    private String mingPinClassName;
    private Integer campusId;
}
