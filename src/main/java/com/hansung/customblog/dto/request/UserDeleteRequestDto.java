package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class UserDeleteRequestDto {

    private String username;

    protected UserDeleteRequestDto() {
    }

    public UserDeleteRequestDto(String username) {
        this.username = username;
    }
}
