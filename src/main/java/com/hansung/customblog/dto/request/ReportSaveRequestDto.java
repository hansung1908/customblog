package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class ReportSaveRequestDto {

    private int targetBoardId;
    private int reporterUserId;
    private String reason;

    protected ReportSaveRequestDto() {
    }

    public ReportSaveRequestDto(int targetBoardId, int reporterUserId, String reason) {
        this.targetBoardId = targetBoardId;
        this.reporterUserId = reporterUserId;
        this.reason = reason;
    }
}
