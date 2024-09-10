package com.chuanglian.mingpin.mapper.campus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chuanglian.mingpin.entity.campus.File;

@TableName("campusManagement.files")
public interface FilesMapper extends BaseMapper<File> {
}
