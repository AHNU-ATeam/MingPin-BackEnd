package com.chuanglian.mingpin.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private MultipartFile[] pictures;

    private MultipartFile[] documents;

    private NotificationVO notificationVO;

}
