package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.album.ClassAlbum;
import com.chuanglian.mingpin.entity.album.Image;
import com.chuanglian.mingpin.entity.album.Video;
import com.chuanglian.mingpin.entity.campus.File;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.album.ClassAlbumMapper;
import com.chuanglian.mingpin.mapper.album.ImagesMapper;
import com.chuanglian.mingpin.mapper.album.VideoMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        for (ImageVO i : classAlbumDTO.getImageVOS()) {
            Image image = new Image();
            image.setUrl(i.getUrl());
            image.setAlbumId(albumId);
            image.setOrderId(i.getOrder());
            imagesMapper.insert(image);
        }

        for (VideoVO v : classAlbumDTO.getVideoVOS()) {
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
        ImageVO[] images = new ImageVO[selectImages.size()];
        VideoVO[] video = new VideoVO[selectVideo.size()];

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

        if (!selectVideo.isEmpty()) {
            for (int i = 0; i < video.length; i++) {
                Video selectVideoSingle = selectVideo.get(i);
                VideoVO videoVO = new VideoVO();
                videoVO.setId(selectVideoSingle.getId());
                videoVO.setOrder(selectVideoSingle.getOrderId());
                videoVO.setUrl(selectVideoSingle.getUrl());
                video[i] = videoVO;
            }
        }


        return Result.success(classAlbumVO);
    }
}
