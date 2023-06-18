package com.hansung.customblog.controller;

import com.hansung.customblog.domain.User;
import com.hansung.customblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/join")
    public void join() {
        User user = new User();
        user.setUsername("kim");
        user.setPassword("1234");
        user.setEmail("kim@naver.com");
        userService.join(user);
    }
}
