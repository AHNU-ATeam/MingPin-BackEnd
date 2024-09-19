package com.chuanglian.mingpin.entity.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVO {
    private int teacherId;
    private String avatarAddress;
    private String sex;  //男 ？ 女
    private String name;
    private String IdentificationNumber;
    private String phone;
    private String permissionStatus;
    private String position;
    private String password;
    private Integer campusId ;  //校区id
}
