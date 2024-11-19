package com.chuanglian.mingpin.entity.campus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("[campusManagement].[campus]")
public class Campus {
    @TableId(value = "campus_id", type = IdType.AUTO )
    private Integer campusId;
    @TableField("logo")
    private String campusLogo; //指地址
    @TableField("name")
    private String name;// 校区名称和校长姓名
    @TableField("principal_name")
    private String principalName;
    @TableField(exist = false)
    private String phone;
    private String region,address;
    private Integer principalId;
    private LocalDate renewalStart,renewalEnd;
    private String info;
    private Integer population;
    private LocalDate createdAt,updatedAt;
    @TableField(exist = false)
    private List<String> campusPics;
    @TableField(exist = false)
    private List<String> teacherPics;

    @TableField("campus_pics_urls")
    private String campusPicsUrls;

    @TableField("teacher_pics_urls")
    private String teacherPicsUrls;

    @TableField("is_deleted")
    private int isDeleted;

    private BigDecimal balance;
}
