package com.chuanglian.mingpin.utils;

import com.chuanglian.mingpin.pojo.UserForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    /**
     * 获取当前登录用户的ID
     *
     * @return 当前用户的ID，如果用户未登录则返回null
     */
    public static Integer getCurrentUserId() {
        // 从SecurityContextHolder获取Authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 检查Authentication是否有效，并且Principal是否为UserForm
        if (authentication != null && authentication.getPrincipal() instanceof UserForm) {
            UserForm userForm = (UserForm) authentication.getPrincipal();
            return userForm.getId(); // 假设UserForm有getId()方法
        }

        // 如果用户未登录或无法获取用户信息，返回null
        return null;
    }
}
