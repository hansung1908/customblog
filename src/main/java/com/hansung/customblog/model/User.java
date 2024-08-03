package com.hansung.customblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity // 테이블 생성
@Data
@Builder
public class User {

    public User() {
    }

    public User(int id, String username, String password, String email, RoleType role, String provider, String providerId, Timestamp createDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(nullable = false, length = 100, unique = true)
    @NotNull(message = "유저네임 키값이 없습니다.")
    @NotBlank(message = "유저네임을 입력하세요.")
    @Size(max = 100, message = "유저네임 길이를 초과하였습니다.")
    private String username;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "비밀번호가 없습니다.")
    private String password;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "이메일이 없습니다.")
    private String email;

    @Enumerated(EnumType.STRING) // 해당 필드를 String 타입이라고 명시
    private RoleType role;

    private String provider;

    private String providerId;

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
}
