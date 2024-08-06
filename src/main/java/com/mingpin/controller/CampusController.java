package com.mingpin.controller;


import com.mingpin.pojo.Campus;
import com.mingpin.pojo.Result;
import com.mingpin.service.CampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utils.AliOSSUtils;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RestController
public class CampusController {

    @Autowired
    private CampService campService;
    private AliOSSUtils aliOSSUtils;

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
        campus.setUpdateAt(LocalDate.now());


        //调用阿里云OSS工具类进行文件上传

        String url = aliOSSUtils.upload(campusLogo);
        log.info("文件上传完成,文件访问的url: {}", url);

        campus.setLogo(url);

        campService.addCampus(campus);

        return Result.success(campus.getLogo());
    }


}
