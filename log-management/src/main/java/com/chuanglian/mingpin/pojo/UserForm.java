package com.chuanglian.mingpin.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm implements UserDetails {

    private LoginForm loginForm;

    private List<String> permissions;

    @JSONField(serialize = false)
    private Set<GrantedAuthority> authorities;

    public UserForm(LoginForm loginForm, List<String> permissions) {
        this.loginForm = loginForm;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        // 传入的权限组，使用Set集合实现去重
        // 将permissions中权限信息封装成实现类
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginForm.getPassword();
    }

    @Override
    public String getUsername() {
        return loginForm.getPhone();
    }

    public Integer getId() {
        return loginForm.getId();
    }
}
