package com.chuanglian.mingpin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SimpleMultipartFile implements MultipartFile {

    private final byte[] content;
    private final String name;
    private final String originalFilename;

    public SimpleMultipartFile(byte[] content, String name, String originalFilename) {
        this.content = content;
        this.name = name;
        this.originalFilename = originalFilename;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(java.io.File dest) {
        throw new UnsupportedOperationException("This operation is not supported");
    }
}
