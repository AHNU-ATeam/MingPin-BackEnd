package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.ClassAlbumVO;
import com.chuanglian.mingpin.pojo.Result;

public interface ClassAlbumService {
    Result<Long> createNewAlbum(ClassAlbumDTO classAlbumDTO);

    Result<ClassAlbumVO> getClassAlbum(Long id);
}
