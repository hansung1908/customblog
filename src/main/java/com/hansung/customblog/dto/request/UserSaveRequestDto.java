package com.hansung.customblog.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    @NotNull(message = "유저네임 키값이 없습니다.")
    @NotBlank(message = "유저네임을 입력하세요.")
    @Size(max = 100, message = "유저네임 길이를 초과하였습니다.")
    private String username;

    @NotBlank(message = "비밀번호가 없습니다.")
    private String password;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "유효한 이메일 주소를 입력해 주세요.")
    private String email;

    protected UserSaveRequestDto() {
    }

    public UserSaveRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
