package com.chuanglian.mingpin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("logManagement.sys_log")
@NoArgsConstructor
public class SysLog {

    /**
     * 主键
     */
    @TableId(value = "log_id", type = IdType.AUTO)  // 主键ID，自动增长
    private Integer logId;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 请求路径
     */
    private String requestUri;

    /**
     * IP 地址
     */
    private String ip;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 执行时间(毫秒)
     */
    private Long executionTime;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 浏览器版本
     */
    private String browserVersion;

    /**
     * 终端系统
     */
    private String os;

    /**
     * 创建人ID
     */
    private Integer createBy;

    /**
     * 创建人用户名
     */
    private String userName;

    /**
     * 逻辑删除标识(1-已删除 0-未删除)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}