package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chuanglian.mingpin.entity.attendance.StudentAttendance;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.leave.Leave;
import com.chuanglian.mingpin.entity.leave.dto.StudentLeaveDTO;
import com.chuanglian.mingpin.entity.leave.dto.TeacherLeaveDTO;
import com.chuanglian.mingpin.entity.leave.vo.LeaveVO;
import com.chuanglian.mingpin.mapper.attendance.StuAttendMapper;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.leave.LeaveMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.LeaveService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    private final LeaveMapper leaveMapper;
    private final UserMapper userMapper;
    private final ClassMgmtMapper classMgmtMapper;
    private final StuAttendMapper stuAttendMapper;

    public LeaveServiceImpl(LeaveMapper leaveMapper, UserMapper userMapper, ClassMgmtMapper classMgmtMapper, StuAttendMapper stuAttendMapper) {
        this.leaveMapper = leaveMapper;
        this.userMapper = userMapper;
        this.classMgmtMapper = classMgmtMapper;
        this.stuAttendMapper = stuAttendMapper;
    }

    @Override
    public void apply(StudentLeaveDTO leaveDTO) {
        Leave leave = new Leave();
        BeanUtils.copyProperties(leaveDTO, leave);
        leaveMapper.insert(leave);
    }

    /**
     * 辅助方法，用于填充 LeaveVO 对象中的用户名、教师名和班级名。
     */
    private void populateLeaveVO(Leave leave, LeaveVO leaveVO) {
        leaveVO.setUserName(userMapper.selectById(leave.getUserId()).getNickname());
        Class c = classMgmtMapper.selectById(leave.getClassId());
        Integer teacherId = c.getUserId();
        leaveVO.setTeacherId(teacherId);
        leaveVO.setTeacherName(userMapper.selectById(teacherId).getNickname());
        leaveVO.setClassName(c.getName());
    }

    @Override
    public LeaveVO findById(Integer leaveId) {
        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Leave::getLeaveId, leaveId)
                .eq(Leave::getStatus, 0);
        Leave leave = leaveMapper.selectOne(queryWrapper);
        LeaveVO leaveVO = new LeaveVO();
        BeanUtils.copyProperties(leave, leaveVO);
        populateLeaveVO(leave, leaveVO);
        return leaveVO;
    }

    @Override
    public List<LeaveVO> findByUser(Integer userId) {
        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Leave::getUserId, userId)
                .eq(Leave::getStatus, 0);
        List<Leave> leaves = leaveMapper.selectList(queryWrapper);
        List<LeaveVO> leaveVOs = new ArrayList<>(leaves.size());
        for (Leave leave : leaves) {
            LeaveVO leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);
            populateLeaveVO(leave, leaveVO);
            leaveVOs.add(leaveVO);
        }
        return leaveVOs;
    }

    @Override
    public void delete(Integer leaveId) {
        LambdaUpdateWrapper<Leave> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Leave::getLeaveId, leaveId)
                .set(Leave::getStatus, 1);
        leaveMapper.update(null, updateWrapper);
    }

    @Override
    public void update(StudentLeaveDTO leaveDTO) {
        Leave leave = new Leave();
        BeanUtils.copyProperties(leaveDTO, leave);
        leaveMapper.updateById(leave);
    }

    @Override
    public List<LeaveVO> findByTeacher(Integer teacherId) {
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getUserId, teacherId);
        Class c = classMgmtMapper.selectOne(queryWrapper);
        Integer classId = c.getId();
        return findByClass(classId);
    }

    @Override
    public List<LeaveVO> findByClass(Integer classId) {
        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Leave::getClassId, classId)
                .eq(Leave::getStatus, 0);
        List<Leave> leaves = leaveMapper.selectList(queryWrapper);
        List<LeaveVO> leaveVOs = new ArrayList<>(leaves.size());
        for (Leave leave : leaves) {
            LeaveVO leaveVO = new LeaveVO();
            BeanUtils.copyProperties(leave, leaveVO);
            populateLeaveVO(leave, leaveVO);
            leaveVOs.add(leaveVO);
        }
        return leaveVOs;
    }

    @Override
    public void updateByTeacher(TeacherLeaveDTO leaveDTO) {
        Leave leave = leaveMapper.selectById(leaveDTO.getLeaveId());
        if (leaveDTO.getLeaveStatus() == 2) {
            LambdaUpdateWrapper<StudentAttendance> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StudentAttendance::getStudentId, leave.getUserId())
                    .set(StudentAttendance::getType, 3);
            stuAttendMapper.update(null, updateWrapper);
        }
        BeanUtils.copyProperties(leaveDTO, leave);
        leaveMapper.updateById(leave);
    }
}
