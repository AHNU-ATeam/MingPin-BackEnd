package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampusTeacherVO {
    @JsonProperty("avatarAddress")
    private String avatar;
    private String sex;  //男 ？ 女
    @JsonProperty("name")
    private String nickname;
    private String identificationNumber;
    @JsonProperty("phone")
    private String boundPhone;
    private String position;
    private String password;
    private Integer campusId ;  //校区id
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
