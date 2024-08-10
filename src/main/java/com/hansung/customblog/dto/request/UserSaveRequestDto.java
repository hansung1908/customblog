package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    String username;
    String password;
    String email;

    protected UserSaveRequestDto() {
    }

    public UserSaveRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
