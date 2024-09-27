package com.chuanglian.mingpin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.point.Point;
import com.chuanglian.mingpin.entity.point.PointRecords;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.point.PointMapper;
import com.chuanglian.mingpin.mapper.point.PointRecordsMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.PointDto;
import com.chuanglian.mingpin.pojo.StudentInPointVo;
import com.chuanglian.mingpin.service.PointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PointServiceImpl implements PointService {
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private ClassMgmtMapper classMgmtMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PointRecordsMapper pointRecordsMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询学生成绩
     *
     * @param studentId
     * @return
     */
    @Override
    public PointDto selectPoint(Integer studentId) {
        LambdaQueryWrapper<Point> wrapper= new LambdaQueryWrapper<Point>().eq(Point::getStudentId,studentId);
        Point point = pointMapper.selectOne(wrapper);
        PointDto pointDto = BeanUtil.copyProperties(point, PointDto.class);
        User student = userMapper.selectById(studentId);
        StudentInPointVo studentInPointVo = BeanUtil.copyProperties(student, StudentInPointVo.class);
        pointDto.setStudent(studentInPointVo);
        return pointDto;
    }

    @Override
    public List<PointDto> selectPointByClass(Integer classId) {
//        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        studentLambdaQueryWrapper.eq(Student::getClassId,classId);
//        List<Student> students = studentMapper.selectList(studentLambdaQueryWrapper);
        List<Integer> studentsId = classMgmtMapper.getStudentsId(classId);
        List<User> students = userMapper.selectBatchIds(studentsId);

//        List<Integer> studentIds = students.stream().map(Student::getStudentId).toList();
        List<StudentInPointVo> studentVOS = BeanUtil.copyToList(students, StudentInPointVo.class);
        LambdaQueryWrapper<Point> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Point::getStudentId,studentsId);
        wrapper.orderByDesc(Point::getPoint);
        List<Point> points = pointMapper.selectList(wrapper);
        List<PointDto> pointDtos = BeanUtil.copyToList(points, PointDto.class);
        Map<Integer, StudentInPointVo> studentVoMap = studentVOS.stream().collect(Collectors.toMap(StudentInPointVo::getId, studentVo -> studentVo));
        pointDtos.forEach(point -> point.setStudent(studentVoMap.get(point.getStudentId())));
        return pointDtos;
    }

    @Override
    @Transactional  // 添加事务管理
    public String clearPointByClassId(Integer classId) {
        List<PointDto> pointDtos = selectPointByClass(classId);
        List<Point> points = BeanUtil.copyToList(pointDtos, Point.class);
//        System.out.println(points);
        List<Integer> studentIds = points.stream().map(Point::getStudentId).toList();
        List<PointRecords> records = new ArrayList<>();
        for (Point point : points) {
            int oldPoint = point.getPoint();  // 记录当前积分

            if (oldPoint != 0) {  // 只有非 0 的积分才需要记录变化
                PointRecords record = new PointRecords();
                record.setStudentId(point.getStudentId());
                record.setPointChange(-oldPoint);  // 积分变化（负数表示减少的积分）
                record.setCreatedAt(LocalDateTime.now());
                record.setType("积分清零");
                records.add(record);
            }
        }
//        System.out.println(records);
        pointRecordsMapper.insertRecords(records);

        pointMapper.resetPointsByStudentIds(studentIds);
        return "修改成功";
    }

    @Override
    public String adjustPoints(Integer studentId, Integer pointsToAdd,String type) {
        PointDto point = selectPoint(studentId);
        int currentPoints = point.getPoint();  // 获取当前积分

        // 如果是减分操作（pointsToAdd 为负数），检查是否有足够积分
        if (pointsToAdd < 0 && currentPoints + pointsToAdd < 0) {
            return "减分失败，当前积分不足";
        }

        // 计算新的积分
        int newPoints = currentPoints + pointsToAdd;
        point.setPoint(newPoints);
        point.setUpdatedAt(LocalDateTime.now());
        Point point1 = BeanUtil.copyProperties(point, Point.class);
        pointMapper.updateById(point1);
        PointRecords record = new PointRecords();
        record.setStudentId(studentId);
        record.setPointChange(pointsToAdd);  // 记录加减分数
        record.setCreatedAt(LocalDateTime.now());
        record.setType(type);
        // 插入积分变动记录
        pointRecordsMapper.insert(record);
        return "修改成功";
    }
}
