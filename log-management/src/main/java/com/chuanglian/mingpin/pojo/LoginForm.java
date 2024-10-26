package com.chuanglian.mingpin.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    private Integer id;

    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号必须是11位")
    private String phone;

    @JSONField(serialize = false)
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 25, message = "密码长度必须在6到25个字符之间")
    private String password;
}
