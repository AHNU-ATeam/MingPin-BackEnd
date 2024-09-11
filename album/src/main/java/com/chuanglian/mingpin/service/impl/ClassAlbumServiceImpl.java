package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.stereotype.Service;

@Service
public class ClassAlbumServiceImpl implements ClassAlbumService {


    @Override
    public Result<Integer> createNewAlbum(ClassAlbumDTO classAlbumDTO) {





        return Result.success();
    }
}
