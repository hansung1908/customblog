package com.hansung.customblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity // 테이블 생성
@Getter
public class User {

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

    protected User() {
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

    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.role = builder.role;
        this.provider = builder.provider;
        this.providerId = builder.providerId;
        this.createDate = builder.createDate;
    }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private String email;
        private RoleType role;
        private String provider;
        private String providerId;
        private Timestamp createDate;

        public Builder() {
        }

        // 기본값 정의 메소드
        // 객체를 찾다가 못 찾을 경우 사용
        public Builder defualt() {
            this.id = 0;
            this.username = null;
            this.password = null;
            this.email = null;
            this.role = null;
            this.provider = null;
            this.providerId = null;
            this.createDate = null;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(RoleType role) {
            this.role = role;
            return this;
        }

        public Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder providerId(String providerId) {
            this.providerId = providerId;
            return this;
        }

        public Builder createDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }

        // 빌드 메서드
        public User build() {
            return new User(this);
        }
    }

    // 비밀번호 업데이트 메서드
    public User updatePassword(String password) {
        return new Builder()
                .id(this.id)
                .username(this.username)
                .password(password)
                .email(this.email)
                .role(this.role)
                .provider(this.provider)
                .providerId(this.providerId)
                .createDate(this.createDate)
                .build();
    }

    // 이메일 업데이트 메서드
    public User updateEmail(String email) {
        return new Builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(email)
                .role(this.role)
                .provider(this.provider)
                .providerId(this.providerId)
                .createDate(this.createDate)
                .build();
    }
}
