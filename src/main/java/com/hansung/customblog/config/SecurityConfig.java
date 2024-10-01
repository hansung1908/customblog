package com.hansung.customblog.config;

import com.hansung.customblog.config.auth.CustomAuthenticationFailureHandler;
import com.hansung.customblog.config.auth.CustomAuthenticationSuccessHandler;
import com.hansung.customblog.config.auth.PrincipalDetailService;
import com.hansung.customblog.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig{

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenConfig tokenConfig;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/auth/joinProc/**")) // user/joinForm 페이지 토큰 인식 불가로 인한 임시 조치
                .authorizeHttpRequests(authroize -> authroize.requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // '/admin/**' 경로는 'ADMIN' 역할을 가진 사용자만 접근 가능
                        .anyRequest()
                        .authenticated())
                .formLogin(formlogin -> formlogin.loginPage("/auth/loginForm")
                        .loginProcessingUrl("/auth/loginProc")
                        .successHandler(authenticationSuccessHandler()) // 로그인 성공 핸들러 설정
                        .failureHandler(authenticationFailureHandler())) // 로그인 실패 핸들러 설정
                .oauth2Login(oauth2login -> oauth2login.loginPage("/auth/loginForm")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(principalOauth2UserService)))
                .rememberMe(me -> me.key("hansung") // 자동 로그인에서 토큰 생성 / 검증에 사용되는 식별키
                        .tokenRepository(tokenConfig.setupTokenRepositoryConnection()) // 토큰을 저장할 repository 설정
                        .tokenValiditySeconds(60 * 60 * 24 * 7)); // 토큰 유효시간 (7일, 초로 계산)

        return http.build();
    }
}
