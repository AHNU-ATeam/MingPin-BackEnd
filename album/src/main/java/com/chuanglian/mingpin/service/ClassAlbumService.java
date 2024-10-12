package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.ClassAlbumInfoVO;
import com.chuanglian.mingpin.pojo.ClassAlbumVO;
import com.chuanglian.mingpin.pojo.Result;

import java.util.List;

public interface ClassAlbumService {
    Result<Long> createNewAlbum(ClassAlbumDTO classAlbumDTO);

    Result<ClassAlbumVO> getAlbumByID(Long id);

    Result updateClassAlbum(Long id, ClassAlbumDTO classAlbumDTO);

    Result deleteClassAlbum(Long id);

    Result<List<ClassAlbumInfoVO>> getAlbumByClass(Long id);
}
