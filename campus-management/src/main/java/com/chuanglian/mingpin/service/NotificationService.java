package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.NotificationVO;
import com.chuanglian.mingpin.pojo.Result;
import com.github.pagehelper.PageInfo;

import java.io.IOException;

public interface NotificationService {

    Result<Integer> postNotification(NotificationDTO notificationDTO) throws IOException;

    Result updateNotification(NotificationDTO notificationDTO);

    Result<NotificationVO> getNotification(Integer id);

    Result<PageInfo<NotificationVO>> getAllNotification(Integer pageNum, Integer pageSize, Long publisher);

    Result deleteNotification(Integer id);

}
