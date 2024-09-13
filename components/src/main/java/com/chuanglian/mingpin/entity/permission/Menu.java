package com.chuanglian.mingpin.entity.permission;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("permissionManagement.menu")
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = -54979041104113736L;

    @TableId
    @NonNull
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String prem;

    private Character status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer delFlag;
}
