package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.mapper.campus.NotificationMapper;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.vo.campus.NotificationVO;
import com.chuanglian.mingpin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public Result<Integer> postNotification(Notification notification) {




        return Result.success(19);
    }

    @Override
    public Result updateNotification(Notification notification) {
        return null;
    }

    @Override
    public Result getNotification(Integer id) {
        return null;
    }

    @Override
    public Result getAllNotification() {
        return null;
    }

    @Override
    public Result deleteNotification(Integer id) {
        return null;
    }
}
