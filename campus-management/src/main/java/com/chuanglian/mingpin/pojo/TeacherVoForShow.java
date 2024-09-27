package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherVoForShow {
    private String avatar;
    private String nickname;
    private String sex;
    private LocalDateTime createdAt;
    private String status;
    private String identificationNumber;
    private Integer campusId;
    private Integer teacherId;
    private String boundPhone;
}
