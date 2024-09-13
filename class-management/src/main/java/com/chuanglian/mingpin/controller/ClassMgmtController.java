package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Class;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.ClassMgmtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  //Lombok 提供的一个注解,可以直接使用 log 来记录日志。
@RestController //@Controller 和 @ResponseBody 的组合注解，表示该类中的所有方法的返回值会直接写入 HTTP 响应体
@RequestMapping("/ClassMgmt") //@RequestMapping 注解在类上时，定义了一个基础路径，所有该类中的方法都会以这个路径为前缀。
public class ClassMgmtController {

    @Autowired  //用于自动注入依赖
    private ClassMgmtService classMgmtService;

    /**
     * 查询班级信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<List<Class>> list(@PathVariable Integer id){
        log.info("查询所有班级信息");
        List<Class> classList = classMgmtService.list(id);
        return Result.success(classList);
    }

    /**
     * 添加班级信息
     * @param cls
     * @return
     */
    @PostMapping
    private Result add(@RequestBody Class cls){
        log.info("添加班级信息{}",cls);
        classMgmtService.add(cls);
        return Result.success();
    }

    /**
     * 删除班级
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    private Result delete(@PathVariable Integer id){
        log.info("删除班级{}",id);
        classMgmtService.delect(id);
        return Result.success();
    }

    @GetMapping("/HeadTeacher")
    private Result getHeadTeacher(@RequestBody Class cls){
        log.info("获取班主任信息");

        return Result.success(classMgmtService.getHeadTeacher(cls.getHeadTeacherId()));
    }

    @GetMapping("/students/{id}")
    private Result getStudents(@PathVariable Integer id){
        return Result.success(classMgmtService.getStudents(id));
    }

    @GetMapping("/assistants")
    private Result getAssistants(@RequestBody Class cls){
        return Result.success(classMgmtService.getAssistants(cls));
    }

    @PutMapping("/changeClass")
    private Result changeClass(@RequestBody Class cls){

        return Result.success(classMgmtService.changeClass(cls));
    }
    @GetMapping("/class/{id}")
    private Result selectClass(@PathVariable Integer id){
        return Result.success(classMgmtService.selectClass(id));
    }

    @GetMapping("/{id}/Teachers")
    private Result getTeachers(@PathVariable Integer id){
        return Result.success(classMgmtService.getTeachers(id));
    }
}
