package com.chuanglian.mingpin.entity.campus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("campusManagement.campus")
public class Campus {
    @JsonProperty("campus_id")
    private Integer campusId;
    private String logo; //指地址
    private String name;
    private String address;
    private Integer principalId;
    private LocalDate renewalStart,renewalEnd;
    private String info;
    private Integer population;
    private LocalDate createdAt,updatedAt;
    private String campusPicsUrls,teacherPicsUrls;
}
