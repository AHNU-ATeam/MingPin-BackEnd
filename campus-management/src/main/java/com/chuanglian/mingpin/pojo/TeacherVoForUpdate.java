package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVoForUpdate {
    private Integer teacherId;
    private String avatar;
    private String nickname;
    private String sex;
    private String identificationNumber;

}
