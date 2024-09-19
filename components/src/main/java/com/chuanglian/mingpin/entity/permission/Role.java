package com.chuanglian.mingpin.entity.permission;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("permissionManagement.role")
public class Role {
    @TableId("id")
    private Integer id;
    private String name;
    private String role;
    private Character status = '0';
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer delFlag = 0;
}
