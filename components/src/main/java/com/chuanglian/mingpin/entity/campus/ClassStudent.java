package com.chuanglian.mingpin.entity.campus;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //自动生成 getter 和 setter 方法，toString()，equals()，hashCode()，以及一个包含所有 final 字段的构造函数。
@AllArgsConstructor //生成一个包含类中所有字段的构造函数
@NoArgsConstructor  //用于生成一个无参构造函数
@TableName("[campusManagement].[class_student]")
public class ClassStudent {
    private int classId;
    private int studentId;
}
