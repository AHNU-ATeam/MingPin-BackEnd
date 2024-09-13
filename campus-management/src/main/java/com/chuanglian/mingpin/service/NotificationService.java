package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.NotificationVO;
import com.chuanglian.mingpin.pojo.Result;

import java.io.IOException;

public interface NotificationService {

    Result<Integer> postNotification(NotificationDTO notificationDTO) throws IOException;

    Result updateNotification(NotificationDTO notificationDTO);

    Result<NotificationVO> getNotification(Integer id);

    Result getAllNotification();

    Result deleteNotification(Integer id);

}
