package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class BoardSaveRequestDto {

    private String title;
    private String content;

    protected BoardSaveRequestDto() {
    }

    public BoardSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
