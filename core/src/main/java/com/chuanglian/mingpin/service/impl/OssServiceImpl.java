package com.chuanglian.mingpin.service.impl;

import com.chuanglian.mingpin.pojo.Result;
import com.chuanglian.mingpin.service.OssService;
import com.chuanglian.mingpin.utils.AliOSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public Result<String> getOssUrl(MultipartFile file) throws IOException {
        String url = aliOSSUtils.upload(file);

        if (url.isEmpty()) {
            return Result.error("上传失败");
        }

        return Result.success("上传成功", url);
    }
}
