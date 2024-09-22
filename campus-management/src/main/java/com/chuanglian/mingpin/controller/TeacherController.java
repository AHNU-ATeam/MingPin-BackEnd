package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.TeacherVO;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 展示所有教师信息
     * @return
     */
    @GetMapping("/showTeacherMessage")
    public Result show(){
        log.info("展示所有教师信息");
        return teacherService.list();
    }

    /**
     * 新增教师
     * @param teacherVO
     * @return
     * @throws IOException
     */
    @PostMapping("/addTeacher")
    public Result add( @ModelAttribute TeacherVO teacherVO) throws IOException {
        log.info("新增教师: {}" , teacherVO);
        return teacherService.add(teacherVO);
    }

    /**
     *软删除：将教师id为teacherId的is_deleted设置为1
     * @param teacherId
     * @return
     */
    //@DeleteMapping("/deleteById/{teacherId}")
    @RequestMapping(value = "/deleteById/{teacherId}", method = RequestMethod.POST)
    public Result deleteById(@PathVariable Integer teacherId){
        log.info("删除教师");
        return teacherService.delete(teacherId);
    }

    /**
     *
     * @param teacherVO
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/update")
    public Result update(@ModelAttribute TeacherVO teacherVO) throws IOException {
        log.info("更新教师信息");
//        调用阿里云OSS工具类进行文件上传
//        String url = aliOSSUtils.upload(teacherVO.getAvatarImg());
//        log.info("文件上传完成,文件访问的url: {}", url);
//        teacherVO.setAvatarAddress(url);

        return teacherService.update(teacherVO);
    }

    /**
     * 根据id查询单个教师信息
     * @param teacherId
     * @return
     */
    @GetMapping("/searchById/{teacherId}")
    public Result getTeacherById(@PathVariable Integer teacherId) {
           log.info("查询教师");
           return teacherService.getTeacherById(teacherId);
    }
}
