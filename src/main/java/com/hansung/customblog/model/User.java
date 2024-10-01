package com.hansung.customblog.model;

import jakarta.persistence.*;
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
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING) // 해당 필드를 String 타입이라고 명시
    private RoleType role;

    private String provider;

    private String providerId;

    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;

    protected User() {
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

    // toBuilder 메서드
    public Builder toBuilder() {
        return new Builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .provider(this.provider)
                .providerId(this.providerId)
                .createDate(this.createDate);
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
}
