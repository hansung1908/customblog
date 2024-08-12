package com.hansung.customblog.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private int id;

    private String username;

    @NotBlank(message = "비밀번호가 없습니다.")
    private String password;

    @NotBlank(message = "이메일이 없습니다.")
    @Email(message = "유효한 이메일 주소를 입력해 주세요.")
    private String email;

    protected UserUpdateRequestDto() {
    }

    public UserUpdateRequestDto(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
