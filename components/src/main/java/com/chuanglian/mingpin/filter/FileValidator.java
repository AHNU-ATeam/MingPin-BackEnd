package com.chuanglian.mingpin.filter;

import com.chuanglian.mingpin.annotation.ValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    private final int MiB = 1024 * 1024;
    private long maxSize;
    private String[] mime;
    private String type;
    private Tika tika = new Tika();

    @Override
    public void initialize(ValidFile validFile) {
        this.type = validFile.type();
        this.mime = validFile.mime();
        this.maxSize = validFile.maxSize();

        // 根据 type 设置默认值
        if ("image".equalsIgnoreCase(type)) {
            // 默认最大10MB
            if (maxSize == -1) {
                maxSize = 10 * MiB;
            }
            if (mime.length == 0) {
                mime = new String[]{"image/jpeg", "image/png", "image/gif"};
            }
        } else if ("video".equalsIgnoreCase(type)) {
            // 默认最大50MB
            if (maxSize == -1) {
                maxSize = 50 * MiB;
            }
            if (mime.length == 0) {
                mime = new String[]{"video/mp4", "video/x-msvideo", "video/quicktime"};
            }
        } else if ("file".equalsIgnoreCase(type)) {
            // 默认最大5MB
            if (maxSize == -1) {
                maxSize = 5 * MiB;
            }
        }
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // 检测文件是否是空文件
        if (file == null || file.isEmpty()) {
            return false;
        }

        // 验证文件扩展名和MIME类型是否匹配
        try {
            if (!isMimeTypeConsistent(file)) {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MimeTypeException e) {
            throw new RuntimeException(e);
        }



        return true;
    }

    /**
     * 检查上传文件的大小是否符合指定的最大限制
     *
     * @param file 上传的 MultipartFile 文件
     * @return 如果文件大小在允许范围内，返回 true；否则返回 false
     */
    public boolean isFileSizeValid(MultipartFile file) {
        // 获取文件大小（字节）
        long fileSize = file.getSize();

        // 打印文件大小
        log.info("上传的文件大小: " + fileSize + " 字节");

        // 检查文件大小是否小于或等于最大限制
        return maxSize == -1 || fileSize <= maxSize;
    }

    /**
     * 验证 MultipartFile 文件的 MIME 类型是否与文件后缀名一致
     *
     * @param multipartFile 上传的文件
     * @return 如果 MIME 类型与文件后缀名一致，返回 true；否则返回 false
     * @throws IOException 如果发生文件读取错误
     */
    public boolean isMimeTypeConsistent(MultipartFile multipartFile) throws IOException, MimeTypeException {
        // 使用Tika解析文件的MIME类型
        Tika tika = new Tika();
        String detectedMimeType = tika.detect(multipartFile.getInputStream());

        // 获取文件的原始名称（包含扩展名）
        String fileName = multipartFile.getOriginalFilename();

        if (fileName == null || !fileName.contains(".")) {
            log.warn("文件名无效或没有扩展名");
            return false;
        }

        // 获取文件后缀名（不含“.”）
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        // 根据后缀名查找标准的MIME类型
        String expectedMimeType = MimeTypes.getDefaultMimeTypes().forName(fileExtension).getName();

        // 打印检测到的MIME类型和预期的MIME类型
        log.info("Detected MIME Type: " + detectedMimeType + ", Expected MIME Type: " + expectedMimeType);

        // 比较检测到的MIME类型和预期的MIME类型
        return detectedMimeType.equals(expectedMimeType);
    }

}
