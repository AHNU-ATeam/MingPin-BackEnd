package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chuanglian.mingpin.entity.campus.File;
import com.chuanglian.mingpin.entity.campus.Image;
import com.chuanglian.mingpin.entity.campus.Notification;
import com.chuanglian.mingpin.entity.permission.UserRole;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.campus.FilesMapper;
import com.chuanglian.mingpin.mapper.campus.ImageMapper;
import com.chuanglian.mingpin.mapper.campus.NotificationMapper;
import com.chuanglian.mingpin.mapper.permission.UserRoleMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.service.NotificationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationMapper notificationMapper;
    private final ImageMapper imageMapper;
    private final FilesMapper filesMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper roleUserMapper;

    public NotificationServiceImpl(
            NotificationMapper notificationMapper,
            ImageMapper imageMapper,
            FilesMapper filesMapper,
            UserMapper userMapper, UserRoleMapper roleUserMapper
    ) {
        this.notificationMapper = notificationMapper;
        this.imageMapper = imageMapper;
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
        this.roleUserMapper = roleUserMapper;
    }

    @Override
    @Transactional
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
            throw new IllegalStateException("未知错误" + e);
        }

        notification.setRecipient(vo.getRecipient());

        // 执行入并获得id
        int row = notificationMapper.insert(notification);
        Integer id = notification.getId();

        if (row == 0) {
            throw new IllegalStateException("上传失败");
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
                        throw new IllegalStateException("上传失败");
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
                        throw new IllegalStateException("上传失败");
                    }
                }
            }
        }

        return Result.success(id);
    }

    @Override
    @Transactional
    public Result updateNotification(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationDTO.getNotificationVO(), notification);



        return Result.error("尚未实现");
    }

    @Override
    @Transactional
    public Result<NotificationVO> getNotification(Integer id) {
        // 通过id获得notification的实体信息
        Notification notification = notificationMapper.selectById(id);

        if (notification == null) {
            throw new IllegalStateException("查询失败");
        }

        if (notification.getStatus() == 1) {
            throw new IllegalStateException("资源不存在");
        }

        NotificationVO notificationVO = getNotificationMediaByNoticeID(notification);

        return Result.success(notificationVO);
    }

    private NotificationVO getNotificationMediaByNoticeID(Notification notification) {
        // 获取对应Notification实体的ID
        Integer id = notification.getId();

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
        if (user == null) {
            throw new IllegalStateException("发布者不存在");
        }
        notificationVO.setPublisher(user.getNickname());
        notificationVO.setRecipient(notification.getRecipient());
        notificationVO.setUpdatedAt(notification.getUpdatedAt());
        notificationVO.setCreatedAt(notification.getCreatedAt());

        return notificationVO;
    }

    @Override
    public Result<PageInfo<NotificationVO>> getAllNotification(Integer pageNum, Integer pageSize, Long publisher) {
        // 启动分页
        PageHelper.startPage(pageNum, pageSize);

        // 获取查询结果，指定id，并排除已被删除的结果
        LambdaQueryWrapper<Notification> getAllNotificationByPublisher = new LambdaQueryWrapper<>();
        getAllNotificationByPublisher
                .eq(Notification::getPublisher, publisher)
                .eq(Notification::getStatus, 0);
        List<Notification> resultSet = notificationMapper.selectList(getAllNotificationByPublisher);

        return Result.success(getNotificationVOPageInfo(resultSet));
    }

    public Result<PageInfo<NotificationVO>> getAllNotificationByRecipient(Integer pageNum, Integer pageSize, Long recipient) {
        // 启动分页
        PageHelper.startPage(pageNum, pageSize);

        // 获取recipient对应的用户权限
        UserRole userRole = roleUserMapper.selectOne(new QueryWrapper<UserRole>().lambda()
                .eq(UserRole::getUserId, recipient)
        );
        int role = userRole.getRoleId();

        // 获取查询结果，指定id，并排除已被删除的结果
        LambdaQueryWrapper<Notification> getAllNotificationByPublisher = new LambdaQueryWrapper<>();
        getAllNotificationByPublisher
                .eq(Notification::getRecipient, role).or().eq(Notification::getRecipient, 1)
                .eq(Notification::getStatus, 0);
        List<Notification> resultSet = notificationMapper.selectList(getAllNotificationByPublisher);

        return Result.success(getNotificationVOPageInfo(resultSet));
    }

    private PageInfo<NotificationVO> getNotificationVOPageInfo(List<Notification> resultSet) {
        // 使用 PageInfo 封装分页信息，基于原始查询结果
        PageInfo<Notification> pageInfo = new PageInfo<>(resultSet);

        // 将分页结果中的记录转换为 VO 对象
        List<NotificationVO> resultVO = resultSet.stream()
                .map(Optional::ofNullable)  // 包装为 Optional 处理 null
                .map(notice -> notice.orElseThrow(() -> new IllegalStateException("数据提取错误")))
                .map(this::getNotificationMediaByNoticeID)  // 转换为 VO
                .toList();

        // 创建新的 PageInfo 对象，使用转换后的 VO 对象列表
        PageInfo<NotificationVO> pageInfoVO = new PageInfo<>();
        pageInfoVO.setList(resultVO);
        pageInfoVO.setPageNum(pageInfo.getPageNum());
        pageInfoVO.setPageSize(pageInfo.getPageSize());
        pageInfoVO.setTotal(pageInfo.getTotal());
        pageInfoVO.setPages(pageInfo.getPages());

        return pageInfoVO;
    }


    @Override
    @Transactional
    public Result deleteNotification(Integer id) {
        // 软删除操作
        Notification notification = new Notification();
        notification.setId(id);
        notification.setStatus(1);

        // 获取更新的行数
        int row = notificationMapper.updateById(notification);

        if (row == 0) {
            throw new IllegalStateException("删除失败");
        }

        return Result.success("删除成功");
    }
}
