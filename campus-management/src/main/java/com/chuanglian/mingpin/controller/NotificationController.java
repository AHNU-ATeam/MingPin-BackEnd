package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.vo.campus.NotificationVO;
import com.chuanglian.mingpin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/notice")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/post")
    public Result postNotice(
            MultipartFile[] files,
            @RequestBody NotificationVO notificationVO) {
        // 合法的照片格式和大小
        long maxPictureSize = 5 * 1024 * 1024; // 5MB
        String[] allowedPictureExtensions = {"jpg", "jpeg", "png", "webp"};

        // 合法的照片格式和大小
        long maxVideoSize = 5 * 1024 * 1024; // 5MB
        String[] allowedVideoExtensions = {"mp4", "mov", "png", "webp"};

        // 检查照片和文件是否合法
        long maxFileSize = 5 * 1024 * 1024; // 5MB
        String[] allowedFileExtensions = {"doc", "docx", "ppt", "pptx", "xls", "xlsx"};




        Notification notification = new Notification();

        return notificationService.postNotification(notification);
    }

    @PostMapping("/update")
    public Result updateNotice(@RequestBody Notification notification) {
        return notificationService.updateNotification(notification);
    }

    @GetMapping("/get/{id}")
    public Result getNotice(@PathVariable("id") Integer id) {
        return notificationService.getNotification(id);
    }

    @GetMapping("/getAll")
    public Result getAllNotice() {
        return notificationService.getAllNotification();
    }

    @PostMapping("/delete/{id}")
    public Result deleteNotice(@PathVariable("id") Integer id) {
        return notificationService.deleteNotification(id);
    }

}
