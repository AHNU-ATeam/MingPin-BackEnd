package com.chuanglian.mingpin.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chuanglian.mingpin.entity.user.vo.TeacherVO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("[userManagement].[teacher]")
public class Teacher {
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Integer teacherId;
    private String name;
    @NonNull
    private String phone;
    @NonNull
    private String permissionStatus;

    //新增
    private String avatarAddress;
    private String sex;
    private String IdentificationNumber;


    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer campusId ;  //校区id

    public Teacher(TeacherVO teacherVO) {
        this.name = teacherVO.getName();
        this.phone = teacherVO.getPhone();
        this.permissionStatus =teacherVO.getPermissionStatus();
        this.avatarAddress = teacherVO.getAvatarAddress();
        this.sex = teacherVO.getSex();
        IdentificationNumber = teacherVO.getIdentificationNumber();
        this.position = teacherVO.getPosition();
    }
}
