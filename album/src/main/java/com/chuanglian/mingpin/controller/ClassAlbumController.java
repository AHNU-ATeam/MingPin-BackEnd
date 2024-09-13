package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.ClassAlbumVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/class/album")
public class ClassAlbumController {

    @Autowired
    private ClassAlbumService classAlbumService;

    @PostMapping("/upload")
    public Result<Long> uploadClassAlbum(@ModelAttribute ClassAlbumVO classAlbumVO) {

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();
        classAlbumDTO.setClassId(classAlbumVO.getClassId());
        classAlbumDTO.setClassName(classAlbumVO.getClassName());
        classAlbumDTO.setImageVOS(classAlbumVO.getImageVOS());
        classAlbumDTO.setVideoVOS(classAlbumVO.getVideoVOS());
        classAlbumDTO.setPublisher(classAlbumVO.getPublisher());

        return classAlbumService.createNewAlbum(classAlbumDTO);
    }

    @GetMapping("/get/{id}")
    public Result<ClassAlbumVO> getClassAlbum(@RequestParam("id") Long id) {
        return classAlbumService.getClassAlbum(id);
    }

}
