package com.chuanglian.mingpin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVO {
    private int teacherId;
    @JsonProperty("avatarAddress")
    private String avatar;
    private String sex;  //男 ？ 女
    @JsonProperty("name")
    private String nickname;
    private String identificationNumber;
    @JsonProperty("phone")
    private String boundPhone;
    private String permissionStatus;
    private String position;
    private String password;
    private Integer campusId ;  //校区id
}
