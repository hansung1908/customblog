package com.hansung.customblog.dto.request;

import com.hansung.customblog.model.NoticeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NoticeSaveRequestDto {

    private NoticeType noticeType;

    @NotBlank(message = "공지사항 제목을 입력하세요.")
    @Size(min = 5, max = 100, message = "공지사항 제목 글자 수는 5~100자 이내로 작성하세요.")
    private String title;

    @NotBlank(message = "공지사항 내용을 입력하세요.")
    @Size(max = 2000, message = "공지사항 내용 글자 수는 2000자 이내로 작성하세요.")
    private String content;

    protected NoticeSaveRequestDto() {
    }

    public NoticeSaveRequestDto(NoticeType noticeType, String title, String content) {
        this.noticeType = noticeType;
        this.title = title;
        this.content = content;
    }
}
