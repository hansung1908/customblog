package com.hansung.customblog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReportSaveRequestDto {

    private int targetBoardId;

    private String targetBoardTitle;

    private int reporterUserId;

    private String reporterUsername;

    @NotBlank(message = "리포트 내용을 입력하세요.")
    @Size(max = 500, message = "리포트 내용 글자 수는 500자 이내로 작성하세요.")
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
