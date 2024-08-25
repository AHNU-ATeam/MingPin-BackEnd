package com.chuanglian.mingpin.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@TableName("[userManagement].[student]")
public class Student {
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId; // 学生ID，自增主键

    @NonNull
    private String studentName; // 学生姓名

    @NonNull
    private String gender; // 性别

    @NonNull
    private LocalDate birthDate; // 出生日期

    @NonNull
    private String calendarType; // 日历类型，公历/农历

    @NonNull
    private String schoolName; // 就读学校

    @NonNull
    private String gradeName; // 年级

    @NonNull
    private String className; // 班级

    @NonNull
    private String parentName; // 家长姓名

    @NonNull
    private String parentPhone; // 家长联系电话

    private String address; // 家庭住址

    private String emergencyContactPhone; // 紧急联系电话

    private String specialCondition; // 特异体质、特定疾病或异常心理状况

    private String serviceItem; // 服务项目

    private String parentSuggestions; // 家长的要求与建议

    private String studentSummary; // 学生情况综述

    private LocalDate admissionDate; // 入学日期

    private String teacherName; // 老师姓名

    private LocalDateTime createdAt; // 创建时间

    private LocalDateTime updatedAt; // 更新时间

    private int classId; // 班级id

    private int campusId; // 校区id
}
