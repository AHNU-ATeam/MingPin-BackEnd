package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.Result;

public interface ClassAlbumService {
    Result<Integer> createNewAlbum(ClassAlbumDTO classAlbumDTO);
}
