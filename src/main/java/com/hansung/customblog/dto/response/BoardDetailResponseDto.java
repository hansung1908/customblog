package com.hansung.customblog.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardDetailResponseDto {

    private int id;

    private String title;

    private String content;

    private int count;

    private int userId;

    private String username;

    private List<ReplyResponseDto> reply;

    protected BoardDetailResponseDto() {
    }

    public BoardDetailResponseDto(int id, String title, String content, int count, int userId, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.count = count;
        this.userId = userId;
        this.username = username;
    }

    public void setReply(List<ReplyResponseDto> reply) {
        this.reply = reply;
    }
}
