package com.hansung.customblog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity // 테이블 생성
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING) // 해당 필드를 String 타입이라고 명시
    private RoleType role;

    private String oauth; // kakao, google

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
}
