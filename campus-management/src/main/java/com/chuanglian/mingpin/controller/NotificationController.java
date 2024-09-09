package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.NotificationVO;
import com.chuanglian.mingpin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/notice")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    /*

        // 合法的照片格式和大小
        long maxPictureSize = 5 * 1024 * 1024; // 5MB
        String[] allowedPictureExtensions = {"jpg", "jpeg", "png", "webp"};

        // 合法的照片格式和大小
        long maxVideoSize = 5 * 1024 * 1024; // 5MB
        String[] allowedVideoExtensions = {"mp4", "mov", "png", "webp"};

        // 检查照片和文件是否合法
        long maxFileSize = 5 * 1024 * 1024; // 5MB
        String[] allowedFileExtensions = {"doc", "docx", "ppt", "pptx", "xls", "xlsx"};

    */


    @PostMapping("/post")
    public Result postNotice(
            MultipartFile[] pictures,
            MultipartFile[] documents,
            @RequestBody NotificationVO notificationVO) throws IOException {

        // TODO 验证逻辑省略

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setPictures(pictures);
        notificationDTO.setDocuments(documents);
        notificationDTO.setNotificationVO(notificationVO);

        return notificationService.postNotification(notificationDTO);
    }

    @PostMapping("/update/{id}")
    public Result updateNotice(
            @PathVariable("id") Integer id,
            MultipartFile[] files,
            @RequestBody NotificationVO notificationVO) {

        NotificationDTO notificationDTO = new NotificationDTO();

        return notificationService.updateNotification(notificationDTO);
    }

    @GetMapping("/get/{id}")
    public Result getNotice(@PathVariable("id") Integer id) {
        return notificationService.getNotification(id);
    }

    @GetMapping("/get-all")
    public Result getAllNotice() {
        return notificationService.getAllNotification();
    }

    @PostMapping("/delete/{id}")
    public Result deleteNotice(@PathVariable("id") Integer id) {
        return notificationService.deleteNotification(id);
    }

}
