package com.hansung.customblog.dto.response;

import lombok.Getter;

@Getter
public class BoardListResponseDto {

    private int id;

    private String title;

    private int count;

    private String username;

    protected BoardListResponseDto() {
    }

    public BoardListResponseDto(int id, String title, int count, String username) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.username = username;
    }
}
