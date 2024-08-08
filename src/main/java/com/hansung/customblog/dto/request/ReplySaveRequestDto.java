package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class ReplySaveRequestDto {
    private int userId;
    private int boardId;
    private String content;

    protected ReplySaveRequestDto() {
    }

    public ReplySaveRequestDto(int userId, int boardId, String content) {
        this.userId = userId;
        this.boardId = boardId;
        this.content = content;
    }
}
