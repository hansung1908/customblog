package com.hansung.customblog.dto.response;

import com.hansung.customblog.model.NoticeType;
import lombok.Getter;

@Getter
public class NoticeListResponseDto {

    private NoticeType noticeType;

    private String title;

    private String content;

    protected NoticeListResponseDto() {
    }

    public NoticeListResponseDto(NoticeType noticeType, String title, String content) {
        this.noticeType = noticeType;
        this.title = title;
        this.content = content;
    }
}
