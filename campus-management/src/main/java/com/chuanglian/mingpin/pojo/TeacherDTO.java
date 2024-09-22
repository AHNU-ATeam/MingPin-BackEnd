package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private Integer teacherId;
    private String name;
    private String phone;
    private String position;
    private String avatar;
    private String sex;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer campusId;
    private String identificationNumber;
}
