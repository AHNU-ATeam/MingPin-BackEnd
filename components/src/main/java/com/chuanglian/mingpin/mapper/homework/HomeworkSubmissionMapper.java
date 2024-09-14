package com.chuanglian.mingpin.mapper.homework;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.homework.HomeworkSubmission;

@TableName("homeworkManagement.homework_submission")
public interface HomeworkSubmissionMapper extends BaseMapper<HomeworkSubmission> {
}
