package com.chuanglian.mingpin.mapper.leave;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.leave.Leave;

@TableName("leaveManagement.leave")
public interface LeaveMapper extends BaseMapper<Leave> {
}
