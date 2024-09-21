package com.chuanglian.mingpin.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo {
    private Integer studentId;
    private String studentName;
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

    private String teacherName;
}
