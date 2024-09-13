package com.chuanglian.mingpin.entity.permission;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("permissionManagement.user_role")
public class UserRole {

    private Integer userId;

    private Integer roleId;

}
