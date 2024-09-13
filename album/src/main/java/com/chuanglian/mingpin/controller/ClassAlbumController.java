package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.ClassAlbumDTO;
import com.chuanglian.mingpin.pojo.ClassAlbumVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.ClassAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class/album")
public class ClassAlbumController {

    static {
        System.out.println(ClassAlbumController.class.getName());
    }

    @Autowired
    private ClassAlbumService classAlbumService;

    @PostMapping("/upload")
    public Result<Long> uploadClassAlbum(@RequestBody ClassAlbumVO classAlbumVO) {

        ClassAlbumDTO classAlbumDTO = new ClassAlbumDTO();
        classAlbumDTO.setClassId(classAlbumVO.getClassId());
        classAlbumDTO.setClassName(classAlbumVO.getClassName());
        classAlbumDTO.setImageVOS(classAlbumVO.getImages());
        classAlbumDTO.setVideoVOS(classAlbumVO.getVideo());
        classAlbumDTO.setPublisher(classAlbumVO.getPublisher());

        return classAlbumService.createNewAlbum(classAlbumDTO);
    }

    @GetMapping("/{id}")
    public Result<ClassAlbumVO> getClassAlbum(@PathVariable("id") Long id) {
        return classAlbumService.getClassAlbum(id);
    }

    @PostMapping("/update")
    public Result updateClassAlbum(@RequestBody ClassAlbumVO classAlbumVO) {
        Long id = classAlbumVO.getId();

        ClassAlbumDTO classAlbumDTO = ClassAlbumDTO.builder()
                .classId(classAlbumVO.getClassId())
                .className(classAlbumVO.getClassName())
                .imageVOS(classAlbumVO.getImages())
                .videoVOS(classAlbumVO.getVideo())
                .publisher(classAlbumVO.getPublisher())
                .build();

        return classAlbumService.updateClassAlbum(id, classAlbumDTO);
    }

}
