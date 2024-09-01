package com.hansung.customblog.controller.api;

import com.hansung.customblog.dto.request.UserCheckNameRequestDto;
import com.hansung.customblog.dto.request.UserSaveRequestDto;
import com.hansung.customblog.dto.request.UserUpdateRequestDto;
import com.hansung.customblog.dto.response.ResponseDto;
import com.hansung.customblog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseDto<String> save(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult, HttpServletRequest request) {
        // 세션이 없거나 false면 회원가입 막음
        if(request.getSession().getAttribute("usernameCheckStatus") != null) {
            if((boolean) request.getSession().getAttribute("usernameCheckStatus")) {
                request.getSession().removeAttribute("usernameCheckStatus");
                userService.save(userSaveRequestDto);
                return new ResponseDto<String>(HttpStatus.OK.value(), "회원가입이 완료되었습니다.");
            } else {
                request.getSession().removeAttribute("usernameCheckStatus");
                return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "유저네임 중복 체크를 통과하셔야 합니다.");
            }
        } else {
            return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "유저네임 중복 체크를 통과하셔야 합니다.");
        }
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
    public ResponseDto<String> checkName(@RequestBody UserCheckNameRequestDto userCheckNameRequestDto, HttpServletRequest request) {
        if(userService.checkName(userCheckNameRequestDto.getUsername()) != 0) {
            request.getSession().setAttribute("usernameCheckStatus", false); // 중복 확인을 위한 세션 추가
            return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "중복입니다.");
        } else {
            request.getSession().setAttribute("usernameCheckStatus", true); // 중복 확인을 위한 세션 추가
            return new ResponseDto<String>(HttpStatus.OK.value(), "사용 가능한 이름입니다.");
        }
    }
}
