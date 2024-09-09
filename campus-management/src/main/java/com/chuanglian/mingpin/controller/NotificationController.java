package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.vo.campus.NotificationVO;
import com.chuanglian.mingpin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/notice")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 合法的照片格式和大小
    long maxPictureSize = 5 * 1024 * 1024; // 5MB
    String[] pictureExtensions = {"jpg", "jpeg", "png", "webp"};
    HashSet<String> allowedPictureExtensions = new HashSet<>();


    // 合法的照片格式和大小
    long maxVideoSize = 5 * 1024 * 1024; // 5MB
    String[] videoExtensions = {"mp4", "mov", "png", "webp"};
    HashSet<String> allowedVideoExtensions = new HashSet<>();


    // 合法的文件格式和大小
    long maxDocumentSize = 5 * 1024 * 1024; // 5MB
    String[] documentExtensions = {"doc", "docx", "ppt", "pptx", "xls", "xlsx"};
    HashSet<String> allowedDocumentExtensions = new HashSet<>();


    {
        addExtensionsToSet(allowedPictureExtensions, pictureExtensions);
        addExtensionsToSet(allowedVideoExtensions, videoExtensions);
        addExtensionsToSet(allowedDocumentExtensions, documentExtensions);
    }

    @PostMapping("/post")
    public Result postNotice(
            MultipartFile[] files,
            @RequestBody NotificationVO notificationVO) {


        // 检查照片和文件是否合法




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

    // 转换String组到HashSet
    private void addExtensionsToSet(Set<String> extensionSet, String[] extensions) {
        for (String ext : extensions) {
            extensionSet.add(ext.toLowerCase());
        }
    }

    // 检查照片是否合法
    private boolean isValidPicture(String fileExtension, long fileSize) {
        return allowedPictureExtensions.contains(fileExtension) && fileSize <= maxPictureSize;
    }

    // 检查视频是否合法
    private boolean isValidVideo(String fileExtension, long fileSize) {
        return allowedVideoExtensions.contains(fileExtension) && fileSize <= maxVideoSize;
    }

    // 检查文件是否合法
    private boolean isValidDocument(String fileExtension, long fileSize) {
        return allowedDocumentExtensions.contains(fileExtension) && fileSize <= maxDocumentSize;
    }
}
