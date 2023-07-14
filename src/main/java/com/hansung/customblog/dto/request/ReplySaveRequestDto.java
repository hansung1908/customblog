package com.hansung.customblog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplySaveRequestDto {
    private int userId;
    private int boardId;
    private String content;
}
