package com.hansung.customblog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplySaveRequestDto {
    private int userId;

    private int boardId;

    @NotBlank(message = "댓글 내용을 입력하세요.")
    @Size(max = 500, message = "댓글 내용 글자 수는 500자 이내로 작성하세요.")
    private String content;

    protected ReplySaveRequestDto() {
    }

    public ReplySaveRequestDto(int userId, int boardId, String content) {
        this.userId = userId;
        this.boardId = boardId;
        this.content = content;
    }
}
