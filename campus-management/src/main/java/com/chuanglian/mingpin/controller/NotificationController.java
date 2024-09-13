package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.pojo.NotificationVO;
import com.chuanglian.mingpin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

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
    @PreAuthorize("hasAuthority('sys:notice:post')")
    public Result<Integer> postNotice(
            @ModelAttribute NotificationVO notificationVO) throws IOException {

        // TODO 验证逻辑省略

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setImageVOS(notificationVO.getImageVOs());
        notificationDTO.setFileVOS(notificationVO.getFilesVOs());
        notificationDTO.setNotificationVO(notificationVO);

        return notificationService.postNotification(notificationDTO);
    }

    @PostMapping("update/{id}")
    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Result updateNotice(
            @PathVariable("id") Integer id,
            @RequestParam(value = "images", required = false) MultipartFile[] pictures,
            @RequestParam(value = "files", required = false) MultipartFile[] documents,
            @RequestParam(value = "change", required = false) HashMap<Integer, String> change,
            @RequestParam(value = "images_swap", required = false) HashMap<Integer, String> picturesSwapUrl,
            @RequestParam(value = "files_swap", required = false) HashMap<Integer, String> documentsSwapUrl,
            @ModelAttribute NotificationVO notificationVO) {

        NotificationDTO notificationDTO = new NotificationDTO();

        return notificationService.updateNotification(notificationDTO);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('sys:notice:select')")
    public Result<NotificationVO> getNotice(@PathVariable("id") Integer id) {
        return notificationService.getNotification(id);
    }

    @PostMapping("/get/all")
    @PreAuthorize("hasAuthority('sys:notice:select')")
    public Result getAllNotice() {
        return notificationService.getAllNotification();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Result deleteNotice(@PathVariable("id") Integer id) {
        return notificationService.deleteNotification(id);
    }

}
