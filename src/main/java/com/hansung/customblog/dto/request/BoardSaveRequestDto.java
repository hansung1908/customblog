package com.hansung.customblog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BoardSaveRequestDto {

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 5, max = 100, message = "제목 글자 수는 5~100자 이내로 작성하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    @Size(max = 2000, message = "내용 글자 수는 2000자 이내로 작성하세요.")
    private String content;

    protected BoardSaveRequestDto() {
    }

    public BoardSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
