package com.hansung.customblog.dto.response;

import lombok.Getter;

@Getter
public class ReplyResponseDto {

    private int id;

    private String content;

    private int userId;

    private String username;

    protected ReplyResponseDto() {
    }

    public ReplyResponseDto(int id, String content, int userId, String username) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.username = username;
    }
}
