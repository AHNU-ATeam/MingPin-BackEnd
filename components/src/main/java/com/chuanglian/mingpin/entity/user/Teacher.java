package com.chuanglian.mingpin.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("[userManagement].[teacher]")
public class Teacher {
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;
    @NonNull
    private Integer userId;
//    private String nickname;
//    private String boundPhone;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer campusId;
    private String sex;
    private String identificationNumber;
    private int status; //0:禁用 1：删除
//    private String permissionStatus;
//    private String avatarAddress;
}
