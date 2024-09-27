package com.chuanglian.mingpin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaUserVo {
    private Integer id;
    @NonNull
    private String boundPhone;

    @NonNull
    private String nickname;


}