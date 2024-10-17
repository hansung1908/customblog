package com.hansung.customblog.dto.response;

import com.hansung.customblog.model.RoleType;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class UserListResponseDto {

    private int id;

    private String username;

    private String email;

    private RoleType role;

    private Timestamp createDate;

    protected UserListResponseDto() {
    }

    public UserListResponseDto(int id, String username, String email, RoleType role, Timestamp createDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createDate = createDate;
    }
}
