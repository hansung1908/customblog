package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {

    private String title;
    private String content;

    protected BoardUpdateRequestDto() {
    }

    public BoardUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
