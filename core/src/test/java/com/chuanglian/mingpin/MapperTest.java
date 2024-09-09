package com.chuanglian.mingpin;

import com.chuanglian.mingpin.entity.user.User;
import com.chuanglian.mingpin.mapper.permission.MenuMapper;
import com.chuanglian.mingpin.mapper.user.UserMapper;
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
}
