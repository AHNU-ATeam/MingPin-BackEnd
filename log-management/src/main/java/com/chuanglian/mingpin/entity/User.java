package com.chuanglian.mingpin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("userManagement.[user]")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NonNull
    @JsonProperty("phone")
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
