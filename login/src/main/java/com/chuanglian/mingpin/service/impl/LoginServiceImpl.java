package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.domain.LoginForm;
import com.chuanglian.mingpin.domain.UserForm;
import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.campus.ClassStudent;
import com.chuanglian.mingpin.entity.permission.Role;
import com.chuanglian.mingpin.entity.permission.UserRole;
import com.chuanglian.mingpin.entity.point.Point;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.CampMapper;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.campus.ClassStudentMapper;
import com.chuanglian.mingpin.mapper.permission.RoleMapper;
import com.chuanglian.mingpin.mapper.permission.UserRoleMapper;
import com.chuanglian.mingpin.mapper.point.PointMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.service.LoginService;
import com.chuanglian.mingpin.utils.JwtUtil;
import com.chuanglian.mingpin.utils.RedisCache;
import com.chuanglian.mingpin.utils.RoleEnum;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final RedisCache redisCache;

    private final UserMapper userMapper;

    private final TeacherMapper teacherMapper;

    private final StudentMapper studentMapper;

    private final CampMapper campMapper;

    private final ClassMgmtMapper classMgmtMapper;

    private final ClassStudentMapper classStudentMapper;

    private final PointMapper pointMapper;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    public LoginServiceImpl(
            AuthenticationManager authenticationManager,
            RedisCache redisCache, UserMapper userMapper,
            TeacherMapper teacherMapper, StudentMapper studentMapper,
            CampMapper campMapper, ClassMgmtMapper classMgmtMapper,
            ClassStudentMapper classStudentMapper,
            PointMapper pointMapper, RoleMapper roleMapper,
            UserRoleMapper userRoleMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.redisCache = redisCache;
        this.userMapper = userMapper;
        this.teacherMapper = teacherMapper;
        this.studentMapper = studentMapper;
        this.campMapper = campMapper;
        this.classMgmtMapper = classMgmtMapper;
        this.classStudentMapper = classStudentMapper;
        this.pointMapper = pointMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

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

        if (!role.getRole().equals(loginForm.getRole())) {
            throw new RuntimeException("用户角色错误");
        }

        // 创建用于返回前端的VO类
        UserVO userVO = null;

        if (role.getRole().equals(RoleEnum.PRINCIPAL.getType())) {
            List<Integer> campusIdList;

            LambdaQueryWrapper<Campus> getCampusId = new LambdaQueryWrapper<>();
            getCampusId.select(Campus::getCampusId).eq(Campus::getPrincipalId, id);
            List<Object> list = campMapper.selectObjs(getCampusId);

            if (list == null || list.size() == 0) {
                throw new RuntimeException("校长暂无任何管理校区");
            }

            campusIdList = list.stream()
                    .map(obj -> (Integer) obj)
                    .collect(Collectors.toList());

            PrincipalVO principalVO = PrincipalVO.builder()
                    .id(user.getId())
                    .role(role.getName())
                    .boundPhone(user.getBoundPhone())
                    .avatar(user.getAvatar())
                    .nickname(user.getNickname())
                    .token(jwt)
                    .campusId(campusIdList)
                    .balance(campMapper.selectById(campusIdList.get(0)).getBalance())
                    .teacherNum(teacherMapper.selectCount(new LambdaQueryWrapper<Teacher>().eq(Teacher::getCampusId, campusIdList.get(0))))
                    .studentNum(studentMapper.selectCount(new LambdaQueryWrapper<Student>().eq(Student::getCampusId, campusIdList.get(0))))
                    .build();

            userVO = principalVO;
        } else if (role.getRole().equals(RoleEnum.TEACHER.getType())) {
            Teacher teacher = teacherMapper.selectById(user.getId());
            LambdaQueryWrapper<Class> getClassId = new LambdaQueryWrapper<>();
            getClassId.eq(Class::getUserId, user.getId());
            Class classInfo = classMgmtMapper.selectOne(getClassId);
            Campus campusInfo = campMapper.selectById(teacher.getCampusId());

            TeacherVO teacherVO = TeacherVO.builder()
                    .id(user.getId())
                    .role(role.getName())
                    .boundPhone(user.getBoundPhone())
                    .avatar(user.getAvatar())
                    .nickname(user.getNickname())
                    .token(jwt)
                    .classId(classInfo.getId())
                    .className(classInfo.getName())
                    .campusId(teacher.getCampusId())
                    .campusName(campusInfo.getName())
                    .build();
            userVO = teacherVO;
        } else if (role.getRole().equals(RoleEnum.STUDENT.getType())) {
            LambdaQueryWrapper<ClassStudent> getClassId = new LambdaQueryWrapper<>();
            getClassId.select(ClassStudent::getClassId).eq(ClassStudent::getStudentId, user.getId());
            List<Integer> classIdList = classStudentMapper.selectObjs(getClassId);
            LambdaQueryWrapper<Point> getPoint = new LambdaQueryWrapper<>();
            getPoint.select(Point::getPoint).eq(Point::getStudentId, user.getId());
            Point point = pointMapper.selectOne(getPoint);
            if (point == null) {
                throw new RuntimeException("用户积分错误");
            }
            Class classInfo = classMgmtMapper.selectById(classIdList.get(0));
            Campus campusInfo = campMapper.selectById(classInfo.getCampusId());

            StudentVO studentVO = StudentVO.builder()
                    .id(user.getId())
                    .role(role.getName())
                    .boundPhone(user.getBoundPhone())
                    .avatar(user.getAvatar())
                    .nickname(user.getNickname())
                    .token(jwt)
                    .classId(classIdList.get(0))
                    .className(classInfo.getName())
                    .campusId(classInfo.getCampusId())
                    .campusName(campusInfo.getName())
                    .point(point.getPoint())
                    .build();
            userVO = studentVO;
        } else {
            throw new RuntimeException("用户角色错误");
        }

        // 完整的用户信息存入Redis
        redisCache.setCacheObject("login:" + userId, userForm);

        if (userVO == null) {
            throw new RuntimeException("用户信息错误");
        }
        return Result.success("登录成功", userVO);
    }
}
