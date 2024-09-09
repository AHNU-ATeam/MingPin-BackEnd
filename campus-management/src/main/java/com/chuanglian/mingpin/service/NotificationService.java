package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.vo.campus.NotificationVO;

public interface NotificationService {

    Result postNotification(Notification notification);

    Result updateNotification(Notification notification);

    Result getNotification(Integer id);

    Result getAllNotification();

    Result deleteNotification(Integer id);

}
