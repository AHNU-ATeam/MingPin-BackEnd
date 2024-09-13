package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.album.ClassAlbum;
import com.chuanglian.mingpin.entity.album.Image;
import com.chuanglian.mingpin.entity.album.Video;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.album.ClassAlbumMapper;
import com.chuanglian.mingpin.mapper.album.ImagesMapper;
import com.chuanglian.mingpin.mapper.album.VideoMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassAlbumServiceImpl implements ClassAlbumService {

    @Autowired
    private ClassAlbumMapper classAlbumMapper;

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<Long> createNewAlbum(ClassAlbumDTO classAlbumDTO) {
        // 提取DTO当中的数据
        ClassAlbum classAlbum = new ClassAlbum();
        classAlbum.setClassId(classAlbumDTO.getClassId());
        classAlbum.setClassName(classAlbumDTO.getClassName());

        try {
            Long publisherID = Long.parseLong(classAlbumDTO.getPublisher());
            classAlbum.setPublisher(publisherID);
        } catch (Exception e) {
            return Result.error(e.toString());
        }

        classAlbumMapper.insert(classAlbum);

        // 在images和video表中存储图片url和顺序
        Long albumId = classAlbum.getId();

        for (ClassAlbumImageVO i : classAlbumDTO.getImageVOS()) {
            Image image = new Image();
            image.setUrl(i.getUrl());
            image.setAlbumId(albumId);
            image.setOrderId(i.getOrder());
            imagesMapper.insert(image);
        }

        for (ClassAlbumVideoVO v : classAlbumDTO.getVideoVOS()) {
            Video video = new Video();
            video.setUrl(v.getUrl());
            video.setAlbumId(albumId);
            video.setOrderId(v.getOrder());
            videoMapper.insert(video);
        }

        return Result.success("上传成功", albumId);
    }

    @Override
    public Result<ClassAlbumVO> getClassAlbum(Long id) {
        // 创建VO对象
        ClassAlbumVO classAlbumVO = new ClassAlbumVO();

        // 查找基本信息
        ClassAlbum classAlbum = classAlbumMapper.selectById(id);
        classAlbumVO.setId(classAlbum.getId());
        classAlbumVO.setClassId(classAlbum.getClassId());
        classAlbumVO.setClassName(classAlbum.getClassName());

        // 获取昵称
        Long publisherId = classAlbum.getPublisher();
        User publisher = userMapper.selectById(publisherId);

        if (publisher == null) {
            return Result.error("昵称获取失败");
        }

        classAlbumVO.setPublisher(publisher.getNickname());

        // 获取对应的图片和视频url
        LambdaQueryWrapper<Image> imageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        imageLambdaQueryWrapper.eq(Image::getAlbumId, id);
        List<Image> selectImages = imagesMapper.selectList(imageLambdaQueryWrapper);

        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(Video::getAlbumId, id);
        List<Video> selectVideo = videoMapper.selectList(videoLambdaQueryWrapper);

        // 将查询的到实体类转换成VO
        ClassAlbumImageVO[] images = new ClassAlbumImageVO[selectImages.size()];
        ClassAlbumVideoVO[] video = new ClassAlbumVideoVO[selectVideo.size()];

        // 将具体信息填入具体VO
        if (!selectImages.isEmpty()) {
            for (int i = 0; i < images.length; i++) {
                Image selectImage = selectImages.get(i);
                ClassAlbumImageVO imageVO = new ClassAlbumImageVO();
                imageVO.setId(selectImage.getId());
                imageVO.setOrder(selectImage.getOrderId());
                imageVO.setUrl(selectImage.getUrl());
                images[i] = imageVO;
            }
        }

        if (!selectVideo.isEmpty()) {
            for (int i = 0; i < video.length; i++) {
                Video selectVideoSingle = selectVideo.get(i);
                ClassAlbumVideoVO videoVO = new ClassAlbumVideoVO();
                videoVO.setId(selectVideoSingle.getId());
                videoVO.setOrder(selectVideoSingle.getOrderId());
                videoVO.setUrl(selectVideoSingle.getUrl());
                video[i] = videoVO;
            }
        }

        // 填入返回VO
        classAlbumVO.setImages(images);
        classAlbumVO.setVideo(video);

        // 添加时间
        classAlbumVO.setCreatedAt(classAlbum.getCreatedAt());
        classAlbumVO.setUpdatedAt(classAlbum.getUpdatedAt());


        return Result.success(classAlbumVO);
    }

    @Override
    public Result updateClassAlbum(Long id, ClassAlbumDTO classAlbumDTO) {
        // 获取待更新的文件VO
        ClassAlbumImageVO[] images = classAlbumDTO.getImageVOS();
        ClassAlbumVideoVO[] video = classAlbumDTO.getVideoVOS();

        // 更新class_album表
        ClassAlbum classAlbum = ClassAlbum.builder()
                .classId(classAlbumDTO.getClassId())
                .className(classAlbumDTO.getClassName())
                .publisher(Long.parseLong(classAlbumDTO.getPublisher()))
                .updatedAt(LocalDateTime.now())
                .build();

        if (classAlbumMapper.updateById(classAlbum) == 0) {
            return Result.error("添加失败");
        }

        for (ClassAlbumImageVO i : images) {
            Image image = Image.builder()
                    .id(i.getId())
                    .url(i.getUrl())
                    .albumId(id)
                    .orderId(i.getOrder())
                    .updatedAt(LocalDateTime.now())
                    .build();

            if (imagesMapper.updateById(image) == 0) {
                return Result.error("添加失败");
            }
        }



        return null;
    }
}
