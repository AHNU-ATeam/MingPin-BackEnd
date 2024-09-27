package com.chuanglian.mingpin.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("userManagement.[user]")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NonNull
    private String boundPhone;

    @NonNull
    private String password;

    private String avatar;

    @NonNull
    private String nickname;

    private Integer boundId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NonNull
    private String status;

}
