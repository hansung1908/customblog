package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class UserCheckNameRequestDto {

    private String username;

    protected UserCheckNameRequestDto() {
    }

    public UserCheckNameRequestDto(String username) {
        this.username = username;
    }
}
