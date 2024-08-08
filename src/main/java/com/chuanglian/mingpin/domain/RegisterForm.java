package com.chuanglian.mingpin.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {

    @NotBlank(message = "角色不能为空")
    private String role;

    @Size(min = 11, max = 11, message = "手机号必须是11位")
    private String phone;

    @Size(min = 6, max = 25, message = "密码长度必须在6到25个字符之间")
    private String password;

}
