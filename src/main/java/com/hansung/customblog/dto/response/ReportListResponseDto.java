package com.hansung.customblog.dto.response;

import lombok.Getter;

@Getter
public class ReportListResponseDto {

    private int targetBoardId;

    private String targetBoardTitle;

    private int reporterUserId;

    private String reporterUsername;

    private String reason;

    protected ReportListResponseDto() {
    }

    public ReportListResponseDto(int targetBoardId, String targetBoardTitle, int reporterUserId, String reporterUsername, String reason) {
        this.targetBoardId = targetBoardId;
        this.targetBoardTitle = targetBoardTitle;
        this.reporterUserId = reporterUserId;
        this.reporterUsername = reporterUsername;
        this.reason = reason;
    }
}
