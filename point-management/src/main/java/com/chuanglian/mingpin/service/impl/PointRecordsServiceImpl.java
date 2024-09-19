package com.chuanglian.mingpin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.point.PointRecords;
import com.chuanglian.mingpin.pojo.PointRecordsVo;
import com.chuanglian.mingpin.entity.user.Student;
import com.chuanglian.mingpin.entity.user.vo.StudentVo;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.point.PointRecordsMapper;
import com.chuanglian.mingpin.mapper.user.StudentMapper;
import com.chuanglian.mingpin.service.PointRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PointRecordsServiceImpl implements PointRecordsService {
    @Autowired
    private PointRecordsMapper pointRecordsMapper;
    @Autowired
    private ClassMgmtMapper classMgmtMapper;
    @Autowired
    private StudentMapper studentMapper;
//    @Override
//    public int addPointRecords(PointRecords pointRecords) {
//        return pointRecordsMapper.insert(pointRecords);
//    }

    @Override
    public List<PointRecordsVo> selectPointRecordsByClass(Integer classId) {
//        List<Integer> studentsId = classMgmtMapper.getStudentsId(classId);
        LambdaQueryWrapper<Student> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Student::getClassId,classId);
        List<Student> students = studentMapper.selectList(wrapper1);
        List<Integer> studentsId = students.stream().map(Student::getStudentId).toList();
//        List<Student> students = studentMapper.selectBatchIds(studentsId);
        List<StudentVo> studentVos = BeanUtil.copyToList(students, StudentVo.class);
        LambdaQueryWrapper<PointRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(PointRecords::getStudentId,studentsId);
        List<PointRecords> pointRecords = pointRecordsMapper.selectList(wrapper);
        List<PointRecordsVo> pointRecordsVos = BeanUtil.copyToList(pointRecords, PointRecordsVo.class);


        Map<Integer, StudentVo> studentVoMap = studentVos.stream().collect(Collectors.toMap(StudentVo::getStudentId, studentVo -> studentVo));
        pointRecordsVos.forEach(pointRecord->pointRecord.setStudent(studentVoMap.get(pointRecord.getStudentId())));
        return pointRecordsVos;
    }

    @Override
    public List<PointRecordsVo> selectPointRecords(Integer studentId) {
        Student student = studentMapper.selectById(studentId);
        StudentVo studentVo = BeanUtil.copyProperties(student, StudentVo.class);
        LambdaQueryWrapper<PointRecords> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PointRecords::getStudentId,studentId);
        List<PointRecords> pointRecords = pointRecordsMapper.selectList(wrapper);
        List<PointRecordsVo> pointRecordsVos = BeanUtil.copyToList(pointRecords, PointRecordsVo.class);
        for (PointRecordsVo pointRecordsVo:pointRecordsVos){
            pointRecordsVo.setStudent(studentVo);
        }

        return pointRecordsVos;
    }

}
