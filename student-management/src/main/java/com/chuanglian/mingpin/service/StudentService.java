package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.user.dto.StudentDTO;
import com.chuanglian.mingpin.entity.user.dto.UpdateStudentDTO;
import com.chuanglian.mingpin.entity.user.vo.StudentInfoVO;
import com.chuanglian.mingpin.entity.user.vo.StudentVO;

import java.util.List;

public interface StudentService {
    List<StudentVO> campusList(Integer campusId);

    List<StudentVO> classList(Integer campusId);

    StudentInfoVO findById(Integer studentId);

    void add(StudentDTO studentDTO);

    void update(UpdateStudentDTO updateStudentDTO);

    void deleteById(Integer studentId);
}
