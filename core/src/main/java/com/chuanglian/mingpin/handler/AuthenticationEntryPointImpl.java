package com.chuanglian.mingpin.handler;

import com.alibaba.fastjson.JSON;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 将登录结果封装成JSON字符串
        Result result = new Result(HttpStatus.SC_UNAUTHORIZED, "用户认证失败", null);
        String json = JSON.toJSONString(result);

        // 登录异常处理
        WebUtils.renderString(response, json);
    }
}
