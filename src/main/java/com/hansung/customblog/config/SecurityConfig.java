package com.hansung.customblog.config;

import com.hansung.customblog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig{

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authroize -> authroize.requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(formlogin -> formlogin.loginPage("/auth/loginForm")
                        .loginProcessingUrl("/auth/loginProc")
                        .defaultSuccessUrl("/"));

        return http.build();
    }
}
