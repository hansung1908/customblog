package com.hansung.customblog.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 로그인 실패 시, 알림을 위한 처리
        request.getSession().setAttribute("errorMessage", "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
        response.sendRedirect("/auth/loginForm"); // 로그인 페이지로 리다이렉트
    }
}
