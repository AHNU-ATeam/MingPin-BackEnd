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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Result<Integer> postNotification(NotificationDTO notificationDTO) {
        // 将图片和文件从DTO中提取出来
        ImageVO[] pictures = notificationDTO.getImageVOS();
        FileVO[] documents = notificationDTO.getFileVOS();

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

        // 执行入并获得id
        int row = notificationMapper.insert(notification);
        Integer id = notification.getId();

        if (row == 0) {
            return Result.error("上传失败");
        }

        // 将图片和文件上传至OSS并保存至数据库
        if (pictures != null) {
            for (ImageVO pic : pictures) {
                if (pic != null) {
                    Image image = new Image();
                    image.setUrl(pic.getUrl());
                    image.setOrderId(pic.getOrder());
                    image.setNoticeId(id);
                    if (imageMapper.insert(image) == 0) {
                        return Result.error("上传失败");
                    }
                }
            }
        }

        if (documents != null) {
            for (FileVO doc : documents) {
                if (doc != null) {
                    File file = new File();
                    file.setUrl(doc.getUrl());
                    file.setOrderId(doc.getOrder());
                    file.setNoticeId(id);
                    if (filesMapper.insert(file) == 0) {
                        return Result.error("上传失败");
                    }
                }
            }
        }

        return Result.success(id);
    }

    @Override
    public Result updateNotification(NotificationDTO notificationDTO) {
        return Result.error("尚未实现");
    }

    @Override
    public Result<NotificationVO> getNotification(Integer id) {
        // 通过id获得notification的实体信息
        Notification notification = notificationMapper.selectById(id);

        if (notification == null) {
            return Result.error("查询失败");
        }

        if (notification.getStatus() == 1) {
            return Result.error("资源不存在");
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
        FileVO[] files = new FileVO[selectFiles.size()];

        if (!selectImages.isEmpty()) {
            for (int i = 0; i < images.length; i++) {
                Image selectImage = selectImages.get(i);
                ImageVO imageVO = new ImageVO();
                imageVO.setId(selectImage.getId());
                imageVO.setOrder(selectImage.getOrderId());
                imageVO.setUrl(selectImage.getUrl());
                images[i] = imageVO;
            }
        }

        if (!selectFiles.isEmpty()) {
            for (int i = 0; i < files.length; i++) {
                File selectFile = selectFiles.get(i);
                FileVO fileVO = new FileVO();
                fileVO.setId(selectFile.getId());
                fileVO.setOrder(selectFile.getOrderId());
                fileVO.setUrl(selectFile.getUrl());
                files[i] = fileVO;
            }
        }


        // 将所有信息封装成NotificationVO
        NotificationVO notificationVO = new NotificationVO();
        notificationVO.setId(notification.getId());
        notificationVO.setTitle(notification.getTitle());
        notificationVO.setContent(notification.getContent());
        notificationVO.setImageVOs(images);
        notificationVO.setFilesVOs(files);

        // 通过user_id查询昵称
        User user = userMapper.selectById(notification.getPublisher());
        notificationVO.setPublisher(user.getNickname());
        notificationVO.setRecipient(notification.getRecipient());
        notificationVO.setUpdatedAt(notification.getUpdatedAt());
        notificationVO.setCreatedAt(notification.getCreatedAt());

        return Result.success(notificationVO);
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
