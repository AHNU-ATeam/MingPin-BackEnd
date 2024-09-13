package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.entity.user.Teacher;
import com.chuanglian.mingpin.entity.user.vo.TeacherVO;
import com.chuanglian.mingpin.mapper.user.TeacherMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Result add(TeacherVO teacherVo) {
        Teacher teacher = new Teacher(teacherVo);
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        if (teacherMapper.add(teacher) == 0) {
            return Result.error("创建失败");
        }
        return Result.success("创建成功");
    }

    @Override
    public Result list() {
        if(teacherMapper.list() == null ){
            return Result.error("查询失败 ");
        }
        return Result.success(teacherMapper.list());
    }

    @Override
    public Result delete(Integer teacherId) {
        if(teacherMapper.delete(teacherId) < 0){
            return Result.error("删除失败");
        }

        return Result.success("删除成功");
    }

    @Override
    public Result update(TeacherVO teacherVO) {
        Teacher teacher = new Teacher(teacherVO);
        teacher.setTeacherId(Math.toIntExact(teacherVO.getTeacherId()));
        teacher.setUpdatedAt(LocalDateTime.now());

        System.out.println(teacherVO.getPassword());

        if(teacherMapper.updateById(teacher) == 0){
            return Result.error("更新失败");
        }
        return Result.success("更新成功");
    }

    @Override
    public Result getTeacherById(Integer teacherId) {
        if(teacherMapper.selectTeacherById(teacherId) == null){
            return Result.error("查无此人");
        }
        return Result.success(teacherMapper.selectTeacherById(teacherId));
    }
}
