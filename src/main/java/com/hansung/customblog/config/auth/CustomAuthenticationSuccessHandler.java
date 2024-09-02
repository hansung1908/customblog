package com.hansung.customblog.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 인증된 사용자 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 권한에 따라 리다이렉트 URL 결정
        String redirectUrl = "/"; // 기본 URL

        if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/admin/dashboard"; // 어드민 페이지 URL
        }

        response.sendRedirect(redirectUrl);
    }
}

