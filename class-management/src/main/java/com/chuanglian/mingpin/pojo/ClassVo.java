package com.chuanglian.mingpin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chuanglian.mingpin.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data   //自动生成 getter 和 setter 方法，toString()，equals()，hashCode()，以及一个包含所有 final 字段的构造函数。
@AllArgsConstructor //生成一个包含类中所有字段的构造函数
@NoArgsConstructor  //用于生成一个无参构造函数
public class ClassVo {
    @TableId(value = "class_id",type = IdType.AUTO)
    private Integer id;     //班级id
    private String name;    //班级名称
    private Integer num;    //班级人数
    private Integer campusId ;  //校区id
    private Integer userId;  //教师id
    private LocalDateTime createdAt;   //创建时间
    private LocalDateTime updatedAt;   //修改时间
    private TeaUserVo teacher;
}
