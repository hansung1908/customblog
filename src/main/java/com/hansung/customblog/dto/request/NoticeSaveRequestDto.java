package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class NoticeSaveRequestDto {
    private String title;
    private String content;

    protected NoticeSaveRequestDto() {
    }

    public NoticeSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
