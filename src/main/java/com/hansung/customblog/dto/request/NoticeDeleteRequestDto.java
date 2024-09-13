package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class NoticeDeleteRequestDto {

    private String title;

    protected NoticeDeleteRequestDto() {
    }

    public NoticeDeleteRequestDto(String title) {
        this.title = title;
    }
}
