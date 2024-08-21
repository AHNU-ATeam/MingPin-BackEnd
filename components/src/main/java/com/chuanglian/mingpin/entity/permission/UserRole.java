package com.chuanglian.mingpin.entity.permission;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permissionManagement.user_role")
public class UserRole {

    private Integer userId;

    private Integer roleId;

}
