package com.chuanglian.mingpin.handler;

import com.alibaba.fastjson.JSON;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 将登录结果封装成JSON字符串
        Result result = new Result(HttpStatus.SC_FORBIDDEN, "无权访问", null);
        String json = JSON.toJSONString(result);

        // 登录异常处理
        WebUtils.renderString(response, json);
    }
}
