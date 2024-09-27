package com.chuanglian.mingpin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.ClassVo;
import com.chuanglian.mingpin.pojo.TeaUserVo;
import com.chuanglian.mingpin.service.ClassMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ClassMgmtServiceImpl implements ClassMgmtService {

    @Autowired
    private ClassMgmtMapper classMgmtMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<ClassVo> list(Integer id) {


        LambdaQueryWrapper <Class> wrapper=new LambdaQueryWrapper<Class>().eq(Class::getCampusId,id);
        List<Class> classes = classMgmtMapper.selectList(wrapper);
        if (classes.isEmpty()) {
            return new ArrayList<>();
        }
        // 提取所有 userId
        Set<Integer> userIds = classes.stream()
                .map(Class::getUserId)
                .collect(Collectors.toSet());
        // 一次性查询所有的用户信息
        List<User> users = userMapper.selectBatchIds(userIds);
        // 将用户信息转换为 Map，方便后续通过 userId 查找用户
        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        // 用于存储结果的 ClassVo 列表
        List<ClassVo> classVoList = new ArrayList<>();
        // 遍历每个班级，查找对应的用户信息
        for (Class aClass : classes) {
            // 获取对应的用户信息
            User user = userMap.get(aClass.getUserId());
            TeaUserVo teaUserVo = BeanUtil.copyProperties(user, TeaUserVo.class);
            // 创建 ClassVo 对象，封装班级和用户信息
            ClassVo classVo = BeanUtil.copyProperties(aClass, ClassVo.class);
            classVo.setTeacher(teaUserVo);

            // 将 ClassVo 添加到结果列表中
            classVoList.add(classVo);
        }
        return classVoList;
    }

    @Override
    public int add(Class cls) {
        cls.setCreatedAt(LocalDateTime.now());
        cls.setUpdatedAt(LocalDateTime.now());
        return classMgmtMapper.insert(cls);
    }

    @Override
    public Integer delect(Integer id) {
        Class aClass = classMgmtMapper.selectById(id);
        if (aClass.getNum()!=0){
            throw new IllegalStateException("班级人数不为0，无法删除");
        }
        return classMgmtMapper.deleteById(id);
    }

//    @Override
//    public Teacher getHeadTeacher(Integer headTeacherId) {
//        return teacherMapper.selectById(headTeacherId);
//    }

//    @Override
//    public List<Student> getStudents(Integer id) {
////        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>().eq(Student::)
//        List<Integer> stuIds = classMgmtMapper.getStudentsId(id);
//
//        return classMgmtMapper.getStudents(stuIds);
//    }


    @Override
    public int changeClass(Class cls) {

        int result = classMgmtMapper.updateById(cls);
        if(result==0){
            throw new RuntimeException("修改失败");
        }
        return result;
    }

//    @Override
//    public Class selectClass(Integer id) {
//        return classMgmtMapper.selectById(id);
//    }

//    @Override
//    public List<Teacher> getTeachers(Integer id) {
//        LambdaQueryWrapper<Teacher> wrapper=new LambdaQueryWrapper<Teacher>().eq(Teacher::getCampusId,id);
//        return teacherMapper.selectList(wrapper);
//    }


}
