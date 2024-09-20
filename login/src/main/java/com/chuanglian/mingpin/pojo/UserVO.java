package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    private Integer id;
    private String role;
    @JsonProperty("campus")
    private List<Integer> campusId;
    @JsonProperty("phone")
    private String boundPhone;
    private String avatar;
    private String nickname;
    private Object token;
}
