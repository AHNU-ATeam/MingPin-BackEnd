package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.mapper.campus.NotificationMapper;
import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.NotificationVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.NotificationService;
import com.chuanglian.mingpin.utils.AliOSSUtils;
import com.chuanglian.mingpin.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public Result<Integer> postNotification(NotificationDTO notificationDTO) throws IOException {
        ArrayList<String> picturesUrl = new ArrayList<>();
        ArrayList<String> documentsUrl = new ArrayList<>();

        MultipartFile[] pictures = notificationDTO.getPictures();
        MultipartFile[] documents = notificationDTO.getDocuments();

        for (MultipartFile file : pictures) {
            String url = aliOSSUtils.upload(file);
            picturesUrl.add(url);
        }

        for (MultipartFile file : documents) {
            String url = aliOSSUtils.upload(file);
            picturesUrl.add(url);
        }

        String picturesJson = JacksonUtils.serializeObject(picturesUrl);
        String documentsJson = JacksonUtils.serializeObject(documentsUrl);

        NotificationVO vo = notificationDTO.getNotificationVO();

        Notification notification = new Notification();
        notification.setTitle(vo.getTitle());
        notification.setContent(vo.getContent());
        notification.setImageUrl(picturesJson);
        notification.setFileUrl(documentsJson);
        notification.setPublisher(vo.getPublisher());
        notification.setRecipient(vo.getRecipient());

        notificationMapper.insert(notification);

        return Result.success(notification.getId());
    }

    @Override
    public Result updateNotification(NotificationDTO notificationDTO) {
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
