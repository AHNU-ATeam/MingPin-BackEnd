package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.NotificationDTO;
import com.chuanglian.mingpin.pojo.NotificationVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.NotificationService;
import com.github.pagehelper.PageInfo;
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

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('sys:notice:post')")
    public Result<Integer> postNotice(@RequestBody NotificationVO notificationVO) throws IOException {

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
    public Result<PageInfo<NotificationVO>> getAllNotice(
            @RequestParam(defaultValue = "1") Integer num,
            @RequestParam(defaultValue = "9999") Integer size,
            @RequestParam Long publisher) {
        return notificationService.getAllNotification(num, size, publisher);
    }

    @PostMapping("/get/all-received")
    @PreAuthorize("hasAuthority('sys:notice:select')")
    public Result<PageInfo<NotificationVO>> getAllReceivedNotice(
            @RequestParam(defaultValue = "1") Integer num,
            @RequestParam(defaultValue = "9999") Integer size,
            @RequestParam Long recipient) {
        return notificationService.getAllNotificationByRecipient(num, size, recipient);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Result deleteNotice(@PathVariable("id") Integer id) {
        return notificationService.deleteNotification(id);
    }

}
