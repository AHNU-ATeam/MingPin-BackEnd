package com.chuanglian.mingpin.controller;

import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/oss")
    public Result<String> getOssUrl(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", required = false) String type) throws IOException {


        // TODO 省略验证逻辑

        return ossService.getOssUrl(file);
    }

}
