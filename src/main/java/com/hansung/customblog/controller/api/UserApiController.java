package com.hansung.customblog.controller.api;

import com.hansung.customblog.dto.request.UserSaveRequestDto;
import com.hansung.customblog.dto.request.UserUpdateRequestDto;
import com.hansung.customblog.dto.response.ResponseDto;
import com.hansung.customblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult) {
        userService.save(userSaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user")
    public ResponseDto<Integer> update(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto, BindingResult bindingResult) {
        userService.update(userUpdateRequestDto);

        // 세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userUpdateRequestDto.getUsername(), userUpdateRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
