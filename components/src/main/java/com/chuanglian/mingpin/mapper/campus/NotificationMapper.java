package com.chuanglian.mingpin.mapper.campus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.campus.Notification;

@TableName("campusManagement.notifications")
public interface NotificationMapper extends BaseMapper<Notification> {
}
