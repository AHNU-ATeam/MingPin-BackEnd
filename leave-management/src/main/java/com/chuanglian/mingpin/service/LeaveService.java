package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.leave.dto.StudentLeaveDTO;
import com.chuanglian.mingpin.entity.leave.dto.TeacherLeaveDTO;
import com.chuanglian.mingpin.entity.leave.vo.LeaveVO;

import java.util.List;

public interface LeaveService {
    void apply(StudentLeaveDTO leaveDTO);

    LeaveVO findById(Integer leaveId);

    List<LeaveVO> findByUser(Integer userId);

    void delete(Integer leaveId);

    void update(StudentLeaveDTO leaveDTO);

    List<LeaveVO> findByTeacher(Integer teacherId);

    List<LeaveVO> findByClass(Integer classId);

    void updateByTeacher(TeacherLeaveDTO leaveDTO);
}
