package com.chuanglian.mingpin.expression;

import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.domain.UserForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ex")
public class ExpressionRoot {

    public boolean hasAuthority(String authority) {
        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserForm loginForm = (UserForm) authentication.getPrincipal();
        List<String> permissions = loginForm.getPermissions();

        // 判断用户的权限集合是否
        return permissions.contains(authority);
    }
}
