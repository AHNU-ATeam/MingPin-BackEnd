package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.domain.UserForm;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.LoginService;
import com.chuanglian.mingpin.utils.JwtUtil;
import com.chuanglian.mingpin.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result<Map<String, String>> login(LoginForm loginForm) {
        // 用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getPhone(), loginForm.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证不通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        // 认证通过，生成JWT，存入Result返回
        UserForm userForm = (UserForm) authenticate.getPrincipal();
        String userId = userForm.getLoginForm().getId().toString();
        // 生成载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        // 编码JWT
        String jwt = JwtUtil.generateJwt(claims);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwt);
        // 完整的用户信息存入Redis
        redisCache.setCacheObject("login:" + userId, userForm);
        return Result.success("登录成功", token);
    }
}
