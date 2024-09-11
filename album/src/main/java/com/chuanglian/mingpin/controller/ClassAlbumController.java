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
    public Result<Integer> uploadClassAlbum(
            @RequestParam(value = "images", required = false) MultipartFile[] pictures,
            @RequestParam(value = "files", required = false) MultipartFile[] documents,
            @ModelAttribute ClassAlbumVO classAlbumVO) {

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();

        return classAlbumService.createNewAlbum(classAlbumDTO);
    }

}
