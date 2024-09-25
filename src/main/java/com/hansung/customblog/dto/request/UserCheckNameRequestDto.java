package com.hansung.customblog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCheckNameRequestDto {

    @NotNull(message = "유저네임 키값이 없습니다.")
    @NotBlank(message = "유저네임을 입력하세요.")
    @Size(max = 100, message = "유저네임 길이를 초과하였습니다.")
    private String username;

    protected UserCheckNameRequestDto() {
    }

    public UserCheckNameRequestDto(String username) {
        this.username = username;
    }
}
