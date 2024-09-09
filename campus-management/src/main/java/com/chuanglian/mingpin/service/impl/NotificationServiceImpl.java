package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuanglian.mingpin.entity.campus.File;
import com.chuanglian.mingpin.entity.campus.Image;
import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.FilesMapper;
import com.chuanglian.mingpin.mapper.campus.ImageMapper;
import com.chuanglian.mingpin.mapper.campus.NotificationMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.service.NotificationService;
import com.chuanglian.mingpin.utils.AliOSSUtils;
import com.chuanglian.mingpin.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public Result<Integer> postNotification(NotificationDTO notificationDTO) throws IOException {
        // 将图片和文件从DTO中提取出来
        MultipartFile[] pictures = notificationDTO.getPictures();
        MultipartFile[] documents = notificationDTO.getDocuments();

        // 将文本信息从DTO中提取出来
        NotificationVO vo = notificationDTO.getNotificationVO();

        // 将前端传入的VO填充进入实体类
        Notification notification = new Notification();
        notification.setTitle(vo.getTitle());
        notification.setContent(vo.getContent());

        // 将前端传入的字符串数字变成真实数字
        try {
            notification.setPublisher(Integer.parseInt(vo.getPublisher()));
        } catch (MissingFormatArgumentException e) {
            return Result.error("未知错误" + e);
        }

        notification.setRecipient(vo.getRecipient());

        // 执行插入并获得id
        int row = notificationMapper.insert(notification);
        Integer id = notification.getId();

        if (row == 0) {
            return Result.error("上传失败");
        }

        // 将图片和文件上传至OSS并保存至数据库
        for (MultipartFile pic : pictures) {
            String url = aliOSSUtils.upload(pic);
            Image image = new Image();
            image.setUrl(url);
            image.setNoticeId(id);
            imageMapper.insert(image);
        }

        for (MultipartFile doc : documents) {
            String url = aliOSSUtils.upload(doc);
            File file = new File();
            file.setUrl(url);
            file.setNoticeId(id);
            filesMapper.insert(file);
        }

        return Result.success(id);
    }

    @Override
    public Result updateNotification(NotificationDTO notificationDTO) {
        return Result.error("尚未实现");
    }

    @Override
    public Result getNotification(Integer id) {
        // 通过id获得notification的实体信息
        Notification notification = notificationMapper.selectById(id);

        if (notification == null) {
            return Result.error("查询失败");
        }

        // 查询对应id并返回符合条件的记录，包含图片和文件url
        QueryWrapper<Image> imageQueryWrapper = new QueryWrapper<>();
        imageQueryWrapper.eq("notice_id", id);
        List<Image> selectImages = imageMapper.selectList(imageQueryWrapper);

        QueryWrapper<File> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("notice_id", id);
        List<File> selectFiles = filesMapper.selectList(fileQueryWrapper);

        // 将查询的到实体类转换成VO
        ImageVO[] images = new ImageVO[selectImages.size()];
        FileVO[] files = new FileVO[selectImages.size()];

        for (int i = 0; i < images.length; i++) {
            Image selectImage = selectImages.get(i);
            ImageVO image = new ImageVO();
            image.setId(selectImage.getId());
            image.setUrl(selectImage.getUrl());
            images[i] = image;
        }

        for (int i = 0; i < files.length; i++) {
            File selectFile = selectFiles.get(i);
            FileVO file = new FileVO();
            file.setId(selectFile.getId());
            file.setUrl(selectFile.getUrl());
            files[i] = file;
        }

        // 将所有信息封装成NotificationVO
        NotificationVO notificationVO = new NotificationVO();
        notificationVO.setId(notification.getId());
        notificationVO.setTitle(notificationVO.getTitle());
        notificationVO.setContent(notification.getContent());
        notificationVO.setImages(images);
        notificationVO.setFiles(files);

        // 通过user_id查询昵称
        User user = userMapper.selectById(notification.getPublisher());
        notificationVO.setPublisher(user.getNickname());
        notificationVO.setRecipient(notification.getRecipient());
        notificationVO.setUpdatedAt(notification.getUpdatedAt());
        notificationVO.setCreatedAt(notification.getCreatedAt());

        return null;
    }

    @Override
    public Result getAllNotification() {
        return Result.error("尚未实现");
    }

    @Override
    public Result deleteNotification(Integer id) {
        // 软删除操作
        Notification notification = new Notification();
        notification.setId(id);
        notification.setStatus(1);

        // 获取更新的行数
        int row = notificationMapper.updateById(notification);

        if (row == 0) {
            return Result.error("删除失败");
        }

        return Result.success("删除成功");
    }
}
