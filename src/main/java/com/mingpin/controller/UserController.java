package com.mingpin.controller;

import com.mingpin.entity.User;
import com.mingpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/find")
    public User findById(Integer id) {
        return userService.findByID(id);
    }
}
