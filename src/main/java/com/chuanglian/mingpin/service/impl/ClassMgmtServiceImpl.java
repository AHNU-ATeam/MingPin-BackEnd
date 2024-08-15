package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.Class;
import com.chuanglian.mingpin.entity.Student;
import com.chuanglian.mingpin.entity.Teacher;
import com.chuanglian.mingpin.mapper.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.StudentMapper;
import com.chuanglian.mingpin.mapper.TeacherMapper;
import com.chuanglian.mingpin.service.ClassMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ClassMgmtServiceImpl implements ClassMgmtService {

    @Autowired
    private ClassMgmtMapper classMgmtMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Class> list(Integer id) {


        LambdaQueryWrapper <Class> wrapper=new LambdaQueryWrapper<Class>().eq(Class::getCampusId,id);
        return classMgmtMapper.selectList(wrapper);
//        return classMgmtMapper.list(id);
    }

    @Override
    public int add(Class cls) {
        return classMgmtMapper.insert(cls);
    }

    @Override
    public Integer delect(Integer id) {
        return classMgmtMapper.deleteById(id);
    }

    @Override
    public Teacher getHeadTeacher(Integer headTeacherId) {
        return teacherMapper.selectById(headTeacherId);
    }

    @Override
    public List<Student> getStudents(Integer id) {
//        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>().eq(Student::)
        List<Integer> stuIds = classMgmtMapper.getStudentsId(id);
        return classMgmtMapper.getStudents(stuIds);
    }

    @Override
    public List<Teacher> getAssistants(Class cls) {
        Integer assistantId1 = cls.getAssistantId1();
        Integer assistantId2 = cls.getAssistantId2();
        Integer assistantId3 = cls.getAssistantId3();
        List<Integer> assistantIds = new ArrayList<>();
        assistantIds.add(assistantId1);
        assistantIds.add(assistantId2);
        assistantIds.add(assistantId3);

        return classMgmtMapper.getAssistants(assistantIds);
    }

    @Override
    public int changeClass(Class cls) {
        cls.setUpdatedAt(LocalDateTime.now());
        return classMgmtMapper.updateById(cls);
    }

    @Override
    public Class selectClass(Integer id) {
        return classMgmtMapper.selectById(id);
    }

    @Override
    public List<Teacher> getTeachers(Integer id) {
        LambdaQueryWrapper<Teacher> wrapper=new LambdaQueryWrapper<Teacher>().eq(Teacher::getCampusId,id);
        return teacherMapper.selectList(wrapper);
    }


}
