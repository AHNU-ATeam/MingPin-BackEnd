package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.ClassAlbumVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class/album")
public class ClassAlbumController {

    private final ClassAlbumService classAlbumService;

    public ClassAlbumController(ClassAlbumService classAlbumService) {
        this.classAlbumService = classAlbumService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('sys:classAlbum:create')")
    public Result<Long> uploadClassAlbum(@RequestBody ClassAlbumVO classAlbumVO) {

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();
        BeanUtils.copyProperties(classAlbumVO, classAlbumDTO);

        return classAlbumService.createNewAlbum(classAlbumDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:classAlbum:select')")
    public Result<ClassAlbumVO> getClassAlbum(@PathVariable("id") Long id) {
        return classAlbumService.getClassAlbum(id);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:classAlbum:update')")
    public Result updateClassAlbum(@RequestBody ClassAlbumVO classAlbumVO) {
        Long id = classAlbumVO.getId();

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();
        BeanUtils.copyProperties(classAlbumVO, classAlbumDTO);

        return classAlbumService.updateClassAlbum(id, classAlbumDTO);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:classAlbum:delete')")
    public Result deleteClassAlbum(@PathVariable("id") Long id) {
        return classAlbumService.deleteClassAlbum(id);
    }
}
