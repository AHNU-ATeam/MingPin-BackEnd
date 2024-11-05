package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.domain.UserForm;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.UserChangePasswordDTO;
import com.chuanglian.mingpin.service.UserService;
import com.chuanglian.mingpin.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserForm userForm = (UserForm) authentication.getPrincipal();
        Integer userId = userForm.getLoginForm().getId();
        redisCache.deleteObject("login:" + userId);
        return Result.success("退出成功");
    }

    @Override
    public Result changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        Integer userId = userChangePasswordDTO.getUserId();
        String oldPassword = userChangePasswordDTO.getOldPassword();
        String newPassword = userChangePasswordDTO.getNewPassword();
        String confirmPassword = userChangePasswordDTO.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalStateException("两次输入的密码不一致");
        }

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalStateException("用户不存在");
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalStateException("原密码错误");
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getPassword, passwordEncoder.encode(newPassword))
                .eq(User::getId, userId);
        int flag = userMapper.update(null, updateWrapper);

        if (flag == 0) {
            throw new IllegalStateException("修改密码失败");
        }

        return Result.success("修改密码成功");
    }


}
