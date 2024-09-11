package com.chuanglian.mingpin.service;

import com.chuanglian.mingpin.pojo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OssService {


    Result<String> getOssUrl(MultipartFile file) throws IOException;
}
