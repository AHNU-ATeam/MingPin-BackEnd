package com.chuanglian.mingpin.filter;

import com.chuanglian.mingpin.annotation.ValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    private long maxSize;
    private String[] allowedExtensions;
    private String fileType;

    @Override
    public void initialize(ValidFile validFile) {
        this.maxSize = validFile.maxSize();
        this.allowedExtensions = validFile.allowedExtensions();
        this.fileType = validFile.fileType();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // 根据 fileType 区分不同类型文件的验证逻辑
        // 对图片、视频等进行不同的大小限制和扩展名校验
        return true;  // 验证逻辑省略
    }
}
