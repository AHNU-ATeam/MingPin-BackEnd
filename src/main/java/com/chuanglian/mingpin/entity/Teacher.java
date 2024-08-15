package com.chuanglian.mingpin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("[userManagement].[teacher]")
public class Teacher {
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;
    private String name;
    @NonNull
    private String phone;
    @NonNull
    private String permissionStatus;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer campusId ;  //校区id
}
