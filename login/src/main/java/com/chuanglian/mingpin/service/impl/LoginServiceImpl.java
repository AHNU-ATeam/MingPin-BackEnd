package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.domain.UserForm;
import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.entity.permission.Role;
import com.chuanglian.mingpin.entity.permission.UserRole;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.attendance.StuAttendInfoMapper;
import com.chuanglian.mingpin.mapper.campus.CampMapper;
import com.chuanglian.mingpin.mapper.permission.RoleMapper;
import com.chuanglian.mingpin.mapper.permission.UserRoleMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.UserVO;
import com.chuanglian.mingpin.service.LoginService;
import com.chuanglian.mingpin.utils.JwtUtil;
import com.chuanglian.mingpin.utils.RedisCache;
import com.chuanglian.mingpin.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CampMapper campMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;



    @Override
    public Result<UserVO> login(LoginForm loginForm) {
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
        Integer id = userForm.getLoginForm().getId();
        String userId = id.toString();
        // 生成载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId);
        // 编码JWT
        String jwt = JwtUtil.generateJwt(claims);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwt);

        // 获取用户信息
        User user = userMapper.selectById(id);
        UserRole userRole = userRoleMapper.selectById(id);
        Integer roleId = userRole.getRoleId();
        Role role = roleMapper.selectById(roleId);
        List<Integer> campusIdList = new ArrayList<>();

        if (role.getRole().equals(RoleEnum.PRINCIPAL)) {
            LambdaQueryWrapper<Campus> getCampusId = new LambdaQueryWrapper();
            getCampusId.select(Campus::getId).eq(Campus::getPrincipalId, id);
            List<Object> list = campMapper.selectObjs(getCampusId);

            campusIdList = list.stream()
                    .map(obj -> (Integer) obj)
                    .collect(Collectors.toList());
        }

        // 创建用于返回前端的VO类
        UserVO userVO = UserVO.builder()
                .id(user.getId())
                .role(role.getName())
                .campusId(campusIdList)
                .boundPhone(user.getBoundPhone())
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .token(jwt)
                .build();

        // 完整的用户信息存入Redis
        redisCache.setCacheObject("login:" + userId, userForm);

        return Result.success("登录成功", userVO);
    }
}
