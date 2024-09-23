package com.chuanglian.mingpin.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("[userManagement].[student]")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;

    private Integer userId;

    private String gender;

    private LocalDate birthDate;

    private String calendarType;

    private String schoolName;

    private String gradeName;

    private String className;

    private String parentName;

    private String parentPhone;

    private String address;

    private String emergencyContactPhone;

    private String specialCondition;

    private String serviceItem;

    private String parentSuggestions;

    private String studentSummary;

    private LocalDate admissionDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int campusId;

    private int status;
}
