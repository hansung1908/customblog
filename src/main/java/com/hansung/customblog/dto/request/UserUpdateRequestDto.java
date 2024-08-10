package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    int id;
    String username;
    String password;
    String email;

    protected UserUpdateRequestDto() {
    }

    public UserUpdateRequestDto(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
