package com.chuanglian.mingpin.mapper.album;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.album.Image;

@TableName("albumManagement.images")
public interface ImagesMapper extends BaseMapper<Image> {
}
