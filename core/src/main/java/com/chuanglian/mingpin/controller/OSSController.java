package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OSSController {

    @PostMapping("/oss")
    public Result<String> getOssUrl(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", required = false) String type) {


        return Result.success();
    }

}
