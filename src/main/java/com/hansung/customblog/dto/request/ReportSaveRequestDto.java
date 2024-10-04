package com.hansung.customblog.dto.request;

import lombok.Getter;

@Getter
public class ReportSaveRequestDto {

    private int targetBoardId;

    private String targetBoardTitle;

    private int reporterUserId;

    private String reporterUsername;

    private String reason;

    protected ReportSaveRequestDto() {
    }

    public ReportSaveRequestDto(int targetBoardId, String targetBoardTitle, int reporterUserId, String reporterUsername, String reason) {
        this.targetBoardId = targetBoardId;
        this.targetBoardTitle = targetBoardTitle;
        this.reporterUserId = reporterUserId;
        this.reporterUsername = reporterUsername;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ReportSaveRequestDto{" +
                "targetBoardId=" + targetBoardId +
                ", targetBoardTitle='" + targetBoardTitle + '\'' +
                ", reporterUserId=" + reporterUserId +
                ", reporterUsername='" + reporterUsername + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
