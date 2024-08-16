package com.chuanglian.mingpin.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("[userManagement].[student]")
public class Student {
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer studentId;
    private String name;
    private String parentName;
    @NonNull
    private String parentPhone;
    private LocalDateTime birthDate;
    private String gender;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
