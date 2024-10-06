package com.chuanglian.mingpin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chuanglian.mingpin.entity.album.ClassAlbum;
import com.chuanglian.mingpin.entity.album.Image;
import com.chuanglian.mingpin.entity.album.Video;
import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.album.ClassAlbumMapper;
import com.chuanglian.mingpin.mapper.album.ImagesMapper;
import com.chuanglian.mingpin.mapper.album.VideoMapper;
import com.chuanglian.mingpin.mapper.campus.ClassMgmtMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.pojo.*;
import com.chuanglian.mingpin.pojo.ClassInfoVO;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassAlbumServiceImpl implements ClassAlbumService {
    private final ClassMgmtMapper classMgmtMapper;

    private final ClassAlbumMapper classAlbumMapper;

    private final ImagesMapper imagesMapper;

    private final VideoMapper videoMapper;

    private final UserMapper userMapper;

    public ClassAlbumServiceImpl(
            ClassMgmtMapper classMgmtMapper,
            ClassAlbumMapper classAlbumMapper,
            ImagesMapper imagesMapper,
            VideoMapper videoMapper,
            UserMapper userMapper
    ) {
        this.classMgmtMapper = classMgmtMapper;
        this.classAlbumMapper = classAlbumMapper;
        this.imagesMapper = imagesMapper;
        this.videoMapper = videoMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public Result<Long> createNewAlbum(ClassAlbumDTO classAlbumDTO) {
        // 提取DTO当中的数据
        // 获取待更新的文件VO
        ClassAlbumImageVO[] images = classAlbumDTO.getImageVOS();
        ClassAlbumVideoVO[] video = classAlbumDTO.getVideoVOS();

        // 填入其他需更新内容
        ClassAlbum classAlbum = new ClassAlbum();
        BeanUtils.copyProperties(classAlbumDTO, classAlbum);

        try {
            Long publisherID = Long.parseLong(classAlbumDTO.getPublisher());
            classAlbum.setPublisher(publisherID);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id不合法", e);
        }

        if (classAlbumMapper.insert(classAlbum) == 0) {
            throw new IllegalStateException("创建相册失败");
        }

        // 在images和video表中存储图片url和顺序
        Long albumId = classAlbum.getId();

        if (images != null) {
            for (ClassAlbumImageVO i : images) {
                Image image = new Image();
                BeanUtils.copyProperties(i, image);
                image.setAlbumId(albumId);
                if (imagesMapper.insert(image) == 0) {
                    throw new IllegalStateException("创建照片池失败");
                }
            }
        }

        if (video != null) {
            for (ClassAlbumVideoVO v : video) {
                Video videoSingle = new Video();
                BeanUtils.copyProperties(v, videoSingle);
                videoSingle.setAlbumId(albumId);
                if (videoMapper.insert(videoSingle) == 0) {
                    throw new IllegalStateException("创建视频池失败");
                }
            }
        }

        return Result.success("上传成功", albumId);
    }

    @Override
    @Transactional
    public Result<ClassAlbumVO> getAlbumByID(Long id) {
        // 创建VO对象
        ClassAlbumVO classAlbumVO = new ClassAlbumVO();

        // 查找基本信息
        ClassAlbum classAlbum = classAlbumMapper.selectById(id);
        BeanUtils.copyProperties(classAlbum, classAlbumVO);

        // 判断是否删除
        if (classAlbum.getStatus() == 1) {
            throw new IllegalStateException("相册已删除");
        }

        // 获取昵称
        Long publisherId = classAlbum.getPublisher();
        User publisher = userMapper.selectById(publisherId);

        if (publisher == null) {
            throw new IllegalStateException("昵称获取失败");
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
                BeanUtils.copyProperties(selectImage, imageVO);
                images[i] = imageVO;
            }
        }

        if (!selectVideo.isEmpty()) {
            for (int i = 0; i < video.length; i++) {
                Video selectVideoSingle = selectVideo.get(i);
                ClassAlbumVideoVO videoVO = new ClassAlbumVideoVO();
                BeanUtils.copyProperties(selectVideoSingle, videoVO);
                video[i] = videoVO;
            }
        }

        // 填入返回VO
        classAlbumVO.setImageVOS(images);
        classAlbumVO.setVideoVOS(video);

        return Result.success(classAlbumVO);
    }

    @Override
    @Transactional
    public Result updateClassAlbum(Long id, ClassAlbumDTO classAlbumDTO) {
        // 获取待更新的文件VO
        ClassAlbumImageVO[] images = classAlbumDTO.getImageVOS();
        ClassAlbumVideoVO[] video = classAlbumDTO.getVideoVOS();

        // 更新class_album表
        ClassAlbum classAlbum = new ClassAlbum();
        BeanUtils.copyProperties(classAlbumDTO, classAlbum);
        classAlbum.setUpdatedAt(LocalDateTime.now());

        // 填入需要更新的id
        classAlbum.setId(id);
        if (classAlbumMapper.updateById(classAlbum) == 0) {
            throw new IllegalStateException("相册更新失败");
        }

        // 更新images表
        if (images != null) {
            for (ClassAlbumImageVO i : images) {
                Image image = new Image();
                BeanUtils.copyProperties(i, image);
                image.setUpdatedAt(LocalDateTime.now());
                if (imagesMapper.updateById(image) == 0) {
                    return Result.error("图片更新失败");
                }
            }
        }

        // 更新video表
        if (video != null) {
            for (ClassAlbumVideoVO v : video) {
                Video videoSingle = new Video();
                BeanUtils.copyProperties(v, videoSingle);
                videoSingle.setUpdatedAt(LocalDateTime.now());
                if (videoMapper.updateById(videoSingle) == 0) {
                    throw new IllegalStateException("视频更新失败");
                }
            }
        }

        return Result.success();
    }

    @Override
    @Transactional
    public Result deleteClassAlbum(Long id) {
        // 软删除操作
        ClassAlbum classAlbum = new ClassAlbum();
        classAlbum.setId(id);
        classAlbum.setStatus(1);

        // 获取更新的行数
        if (classAlbumMapper.updateById(classAlbum) == 0) {
            throw new IllegalStateException("删除失败或该条记录不存在");
        }

        return Result.success("删除成功");
    }

    @Override
    public Result<List<ClassAlbumInfoVO>> getAlbumByClass(Long id) {
        List<ClassAlbumInfoVO> infoVOS = new ArrayList<>();

        LambdaQueryWrapper<ClassAlbum> selectClassAlbumByClass = new LambdaQueryWrapper<>();
        selectClassAlbumByClass.eq(ClassAlbum::getClassId, id);
        List<ClassAlbum> resultSet = classAlbumMapper.selectList(selectClassAlbumByClass);

        for (ClassAlbum album : resultSet) {
            ClassAlbumInfoVO info = new ClassAlbumInfoVO();

            if (album.getStatus() == 1) {
                continue;
            }

            Long publisherId = album.getPublisher();
            Long classId = album.getClassId();

            Class classInfo = classMgmtMapper.selectById(classId);
            ClassInfoVO classInfoVO = new ClassInfoVO();
            BeanUtils.copyProperties(classInfo, classInfoVO);

            User publisher = userMapper.selectById(publisherId);
            PublisherVO publisherVO = new PublisherVO();
            BeanUtils.copyProperties(publisher, publisherVO);

            info.setId(album.getId());
            info.setTitle(album.getTitle());
            info.setClassInfo(classInfoVO);
            info.setPublisher(publisherVO);

            infoVOS.add(info);
        }

        return Result.success(infoVOS);
    }
}
