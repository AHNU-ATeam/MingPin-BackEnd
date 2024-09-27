package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.pojo.PageBean;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.CampService;
import com.chuanglian.mingpin.utils.AliOSSUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CampusController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    private CampService campService;


    /**
     *
     * @param campus
     * @return
     * @throws IOException
     */
    @PostMapping("/camp")
    @PreAuthorize("hasAuthority('sys:campus:create')")
    public Result addCampus(@RequestBody Campus campus) throws IOException {

//        Campus campus = new Campus();
//        campus.setName(name);
//        campus.setRegion(region);
//        campus.setPrincipalName(principalName);
//        campus.setAddress(address);
//        campus.setPrincipalId(principalId);
//        campus.setRenewalStart(renewalStart);
//        campus.setRenewalEnd(renewalEnd);
//        campus.setInfo(info);
//        campus.setPopulation(population);
        campus.setCreatedAt(LocalDate.now());
        campus.setUpdatedAt(LocalDate.now());
        campus.setIsDeleted(1);
//
//        campus.setLogoUrl(campusLogo);


        //存照片数组

       // 使用 Jackson 将 ArrayList 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String campusPicsUrlsJson = objectMapper.writeValueAsString(campus.getCampusPics());
            log.info("将list转成json");
            //System.out.println(campusPicsUrlsJson);
            campus.setCampusPicsUrls(campusPicsUrlsJson);

            String teacherPicsUrlsJson = objectMapper.writeValueAsString(campus.getTeacherPics());
            log.info("将list转成json");
            //System.out.println(teacherPicsUrlsJson);
            campus.setTeacherPicsUrls(teacherPicsUrlsJson);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        


        if(campService.addCampus(campus).getMassage().equals("添加校区失败")){
            return Result.error("添加校区失败");
        }

        return Result.success(campus.getCampusLogo());
    }

    /**
     * 分页展示校区
     * @param page
     * @param pageSize
     * @param name
     * @param begin
     * @param end
     * @return
     */

    @GetMapping("/SearchCampus")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询, 参数: {},{},{},{},{}",page,pageSize,name,begin,end);
        //调用service分页查询
        PageBean pageBean = campService.page(page,pageSize,name,begin,end);
        return Result.success(pageBean);
    }

    @GetMapping("/searchAllCampus")
    public Result searchAllCampus(){
        log.info("展示所有校区");
        return campService.getAllCampus();
    }

    /**
     * 删除校区
     * @param id
     * @return
     */
    //@DeleteMapping("/deleteByids")
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public Result delete(@PathVariable Integer id){
        log.info("删除操作, id:{}",id);

        return campService.delete(id);
    }


    /**
     * 更新校区
     * @param campus
     * @return
     */
    @PostMapping("/updateCampus")
    public Result update(@RequestBody Campus campus){
        log.info("更新校区信息 : {}", campus);

        // 使用 Jackson 将 ArrayList 转换为 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String campusPicsUrlsJson = objectMapper.writeValueAsString(campus.getCampusPics());
            log.info("将list转成json");
            //System.out.println(campusPicsUrlsJson);
            campus.setCampusPicsUrls(campusPicsUrlsJson);

            String teacherPicsUrlsJson = objectMapper.writeValueAsString(campus.getTeacherPics());
            log.info("将list转成json");
            //System.out.println(teacherPicsUrlsJson);
            campus.setTeacherPicsUrls(teacherPicsUrlsJson);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return campService.update(campus);  }




}
