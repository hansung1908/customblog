package com.hansung.customblog.controller.api;

import com.hansung.customblog.dto.ResponseDto;
import com.hansung.customblog.model.User;
import com.hansung.customblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


}
