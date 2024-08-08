package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.Principal;
import com.chuanglian.mingpin.entity.Student;
import com.chuanglian.mingpin.entity.Teacher;
import com.chuanglian.mingpin.entity.User;
import com.chuanglian.mingpin.mapper.PrincipalMapper;
import com.chuanglian.mingpin.mapper.StudentMapper;
import com.chuanglian.mingpin.mapper.TeacherMapper;
import com.chuanglian.mingpin.mapper.UserMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.RegisterService;
import com.chuanglian.mingpin.utils.RedisCache;
import com.chuanglian.mingpin.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrincipalMapper principalMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User checkUserExists(String phone) {
        // 在user表中根据手机号查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getBoundPhone, phone);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Result register(Role role, String phone, String password) {
        // 加密
        String encoded = passwordEncoder.encode(password);

        // 按照不同角色添加账号信息
        int id;
        switch (role) {
            case PRINCIPAL -> {
                // 校长初始化信息
                Principal principal = Principal.builder()
                        .phone(phone)
                        .permissionStatus("principal")
                        .build();
                // 在principal表中插入信息
                principalMapper.insert(principal);

                // 在principal表中根据手机号查询
                LambdaQueryWrapper<Principal> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Principal::getPhone, phone);
                principal = principalMapper.selectOne(queryWrapper);
                id = principal.getPrincipalId();
            }
            case TEACHER -> {
                // 教师初始化信息
                Teacher teacher = Teacher.builder()
                        .phone(phone)
                        .permissionStatus("teacher")
                        .build();
                // 在teacher表中插入信息
                teacherMapper.insert(teacher);

                // 在teacher表中根据手机号查询
                LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Teacher::getPhone, phone);
                teacher = teacherMapper.selectOne(queryWrapper);
                id = teacher.getTeacherId();
            }
            case STUDENT -> {
                // 学生初始化信息
                Student student = Student.builder()
                        .parentPhone(phone)
                        .build();
                student.setParentPhone(phone);
                // 在student表中插入信息
                studentMapper.insert(student);

                // 在student表中根据手机号查询
                LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Student::getParentPhone, phone);
                student = studentMapper.selectOne(queryWrapper);
                id = student.getStudentId();
            }
            default -> {
                return Result.error("角色类型不存在");
            }
        }

        // 获取到对应的id，并添加到user表中
        User user = User.builder()
                .role(role)
                .boundPhone(phone)
                .password(encoded)
                .nickname(phone)
                .boundId(id)
                .status("enable")
                .build();
        userMapper.insert(user);
        return Result.success("注册成功");
    }
}
