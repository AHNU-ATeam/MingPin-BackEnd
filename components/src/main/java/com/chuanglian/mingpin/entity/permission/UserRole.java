package com.chuanglian.mingpin.entity.permission;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permissionManagement.user_role")
public class UserRole {
    @TableId("user_id")
    private Integer userId;

    private Integer roleId;

}
