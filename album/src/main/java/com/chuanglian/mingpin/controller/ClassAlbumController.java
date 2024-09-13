package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.ClassAlbumVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class/album")
public class ClassAlbumController {

    private final ClassAlbumService classAlbumService;

    public ClassAlbumController(ClassAlbumService classAlbumService) {
        this.classAlbumService = classAlbumService;
    }

    @PostMapping("/upload")
    public Result<Long> uploadClassAlbum(@RequestBody ClassAlbumVO classAlbumVO) {

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();
        BeanUtils.copyProperties(classAlbumVO, classAlbumDTO);

        return classAlbumService.createNewAlbum(classAlbumDTO);
    }

    @GetMapping("/{id}")
    public Result<ClassAlbumVO> getClassAlbum(@PathVariable("id") Long id) {
        return classAlbumService.getClassAlbum(id);
    }

    @PostMapping("/update")
    public Result updateClassAlbum(@RequestBody ClassAlbumVO classAlbumVO) {
        Long id = classAlbumVO.getId();

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();
        BeanUtils.copyProperties(classAlbumVO, classAlbumDTO);

        return classAlbumService.updateClassAlbum(id, classAlbumDTO);
    }

    @PostMapping("/delete/{id}")
    public Result deleteClassAlbum(@PathVariable("id") Long id) {
        return classAlbumService.deleteClassAlbum(id);
    }
}
