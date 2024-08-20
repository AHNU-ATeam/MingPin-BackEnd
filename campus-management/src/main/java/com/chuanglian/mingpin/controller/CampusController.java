package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.entity.campus.Campus;
import com.chuanglian.mingpin.pojo.PageBean;
import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.CampService;
import com.chuanglian.mingpin.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
public class CampusController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Autowired
    private CampService campService;


    /**
     * 创建新校区
     * @param campusLogo
     * @param name
     * @param address
     * @param principalId
     * @param renewalStart
     * @param renewalEnd
     * @param info
     * @param population
     * @return
     * @throws IOException
     */
    @PostMapping("/camp")
    public Result addCampus(MultipartFile campusLogo,
                            String name, String address, Integer principalId,
                            @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate renewalStart,
                            @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate renewalEnd,
                            String info, Integer population) throws IOException {

        Campus campus = new Campus();
        campus.setName(name);
        campus.setAddress(address);
        campus.setPrincipalId(principalId);
        campus.setRenewalStart(renewalStart);
        campus.setRenewalEnd(renewalEnd);
        campus.setInfo(info);
        campus.setPopulation(population);
        campus.setCreatedAt(LocalDate.now());
        campus.setUpdatedAt(LocalDate.now());


        //调用阿里云OSS工具类进行文件上传


        String url = aliOSSUtils.upload(campusLogo);
        log.info("文件上传完成,文件访问的url: {}", url);

        campus.setLogo(url);

        campService.addCampus(campus);

        return Result.success(campus.getLogo());
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

    /**
     * 批量删除（可单）校区
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteByids")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        campService.delete(ids);
        return Result.success();
    }


    /**
     * 更新校区
     * @param campus
     * @return
     */
    @PutMapping("/updateCampus")
    public Result update(@RequestBody Campus campus){
        log.info("更新校区信息 : {}", campus);
        campService.update(campus);
        return Result.success();
    }




}
