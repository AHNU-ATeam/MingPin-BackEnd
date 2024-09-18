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

    private ImageVO[] imageVOS;

    private FileVO[] fileVOS;

    private NotificationVO notificationVO;

}
