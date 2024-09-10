package com.chuanglian.mingpin.annotation;

import com.chuanglian.mingpin.filter.FileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "Invalid file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    long maxSize() default -1; // 默认最大文件大小，-1 表示不限制
    String[] allowedExtensions() default {}; // 允许的文件扩展名
    String fileType(); // 文件类型（例如 "image" 或 "video"）
}
