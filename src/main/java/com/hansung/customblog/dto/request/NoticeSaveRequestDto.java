package com.hansung.customblog.dto.request;

import com.hansung.customblog.model.NoticeType;
import lombok.Getter;

@Getter
public class NoticeSaveRequestDto {

    private NoticeType noticeType;
    private String title;
    private String content;

    protected NoticeSaveRequestDto() {
    }

    public NoticeSaveRequestDto(NoticeType noticeType, String title, String content) {
        this.noticeType = noticeType;
        this.title = title;
        this.content = content;
    }
}
