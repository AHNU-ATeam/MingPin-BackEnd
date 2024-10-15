package com.chuanglian.mingpin.interceptors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.chuanglian.mingpin.pojo.LoginForm;
import com.chuanglian.mingpin.entity.SysLog;
import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.mapper.UserMapper;
import com.chuanglian.mingpin.service.SysLogService;
import com.chuanglian.mingpin.utils.IPUtils;
import com.chuanglian.mingpin.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SysLogInterceptor {
    private final SysLogService sysLogService;
    private final HttpServletRequest request;
    @Qualifier("logUserMapper")
    private final UserMapper userMapper;

    public SysLogInterceptor(SysLogService sysLogService, HttpServletRequest request, UserMapper userMapper) {
        this.sysLogService = sysLogService;
        this.request = request;
        this.userMapper = userMapper;
    }

    @Pointcut("@annotation(com.chuanglian.mingpin.security.Log)")
    public void logPointcut() {
    }

    @Around("logPointcut() && @annotation(logAnnotation)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, com.chuanglian.mingpin.security.Log logAnnotation) throws Throwable {
        String requestURI = request.getRequestURI();

        String usernameOrPhone = "未知用户";  // 默认值
        boolean isLoginRequest = false;  // 标识是否为登录请求

        // 判断是否是登录请求，如果是，从参数中获取 loginUserDTO 中的手机号
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof LoginForm loginForm) {
            usernameOrPhone = loginForm.getPhone();  // 从 loginUserDTO 中获取手机号
            isLoginRequest = true;
        }

        // 如果不是登录请求，从 ThreadLocal 获取当前用户信息
        Integer userId = null;
        if (!isLoginRequest) {
            userId = UserUtils.getCurrentUserId();
            if (userId != null) {
                User user = userMapper.selectById(userId);
                if (user != null) {
                    usernameOrPhone = user.getNickname();  // 获取用户的昵称
                }
            } else {
                System.out.println("ThreadLocal 中没有找到用户信息");
            }
        }

        TimeInterval timer = DateUtil.timer();
        // 执行方法
        Object proceed = joinPoint.proceed();
        long executionTime = timer.interval();

        // 创建日志记录
        SysLog log = new SysLog();
        log.setContent(logAnnotation.value());
        log.setRequestUri(requestURI);
        log.setCreateBy(userId);
        log.setUserName(usernameOrPhone);
        String ipAddr = IPUtils.getIpAddr(request);
        if (StrUtil.isNotBlank(ipAddr)) {
            log.setIp(ipAddr);
            String region = IPUtils.getRegion(ipAddr);
            // 中国|0|四川省|成都市|电信 解析省和市
            if (StrUtil.isNotBlank(region)) {
                String[] regionArray = region.split("\\|");
                if (regionArray.length > 2) {
                    log.setProvince(regionArray[2]);
                    log.setCity(regionArray[3]);
                }
            }
        }
        log.setExecutionTime(executionTime);
        // 获取浏览器和终端系统信息
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentString);
        // 系统信息
        log.setOs(userAgent.getOs().getName());
        // 浏览器信息
        log.setBrowser(userAgent.getBrowser().getName());
        log.setBrowserVersion(userAgent.getBrowser().getVersion(userAgentString));
        // 保存日志到数据库
        sysLogService.insert(log);

        return proceed;
    }


}