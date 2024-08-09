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
@TableName("[userManagement].[principal]")
public class Principal {
    @TableId(value = "principal_id", type = IdType.AUTO)
    private Integer principalId;
    private String name;
    @NonNull
    private String phone;
    @NonNull
    private String permissionStatus;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
