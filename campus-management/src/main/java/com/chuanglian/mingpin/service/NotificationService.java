package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.Result;

import java.io.IOException;

public interface NotificationService {

    Result postNotification(NotificationDTO notificationDTO) throws IOException;

    Result updateNotification(NotificationDTO notificationDTO);

    Result getNotification(Integer id);

    Result getAllNotification();

    Result deleteNotification(Integer id);

}
