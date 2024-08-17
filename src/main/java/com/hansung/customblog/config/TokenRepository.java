package com.hansung.customblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class TokenRepository {

    @Autowired
    private DataSource dataSource; // application.yml의 datasource를 객체로 가져옴

    @Bean
    public PersistentTokenRepository setupTokenRepositoryConnection() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl(); // 토큰 저장 db를 등록하는 객체
        jdbcTokenRepository.setCreateTableOnStartup(false); // 토큰에 대한 테이블 생성 유무, 한번 생성했으면 false로 변경
        jdbcTokenRepository.setDataSource(dataSource); // 테이블을 연결하기 위한 데이터소스 (db driver, db url, 아이디, 비번)
        return jdbcTokenRepository;
    }
}
