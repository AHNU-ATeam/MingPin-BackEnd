package com.chuanglian.mingpin;

import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.permission.MenuMapper;
import com.chuanglian.mingpin.mapper.point.PointMapper;
import com.chuanglian.mingpin.mapper.point.PointRecordsMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
import com.chuanglian.mingpin.service.PointService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = MingPinApplication.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private PointRecordsMapper pointRecordsMapper;


    @Test
    public void TestPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testSelectPermsByUserId() {
        List<String> list = menuMapper.selectPermsByUserID(17);
        System.out.println(list);
    }

    @Test
    public void testStringToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> urlList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            urlList.add("url://" + i);
        }

        String json = objectMapper.writeValueAsString(urlList);

        System.out.println(json);

    }

    @Test
    public void testCreateStudentPoint() {
        int studentId = 1001;  // 测试用学生ID
        int initialPoint = 10;  // 初始化积分

        // 调用创建学生积分
        pointMapper.createStudentPoint(studentId, initialPoint);

    }

    @Test
    public void testDeleteStudentPoint() {
        int studentId = 1001;

        // 删除学生积分
        pointMapper.deleteStudentPoint(studentId);
    }


    @Test
    public void testDeleteStudentPointRecords() {
        int studentId = 1003;

        // 删除积分记录
        pointRecordsMapper.deleteStudentPointRecords(studentId);
    }
}
