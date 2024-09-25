package com.chuanglian.mingpin.mapper.homework;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.homework.HomeworkAssignment;

@TableName("homeworkManagement.homework_assignment")
public interface HomeworkAssignmentMapper extends BaseMapper<HomeworkAssignment> {
}
