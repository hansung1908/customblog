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
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<String> save(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult) {
        userService.save(userSaveRequestDto);
        return new ResponseDto<String>(HttpStatus.OK.value(), "회원가입이 완료되었습니다.");
    }

    @PutMapping("/user")
    public ResponseDto<String> update(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto, BindingResult bindingResult) {
        userService.update(userUpdateRequestDto);

        // 세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userUpdateRequestDto.getUsername(), userUpdateRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDto<String>(HttpStatus.OK.value(), "회원수정이 완료되었습니다.");
    }

    @PostMapping("/auth/joinProc/checkName")
    public ResponseDto<String> checkName(@RequestBody String name) {
        System.out.println(name);

        return new ResponseDto<String>(HttpStatus.OK.value(), "사용 가능한 이름입니다.");
    }
}
