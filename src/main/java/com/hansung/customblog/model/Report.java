package com.hansung.customblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int targetBoardId;

    private String targetBoardTitle;

    private int reporterUserId;

    private String reporterUsername;

    private String reason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Report() {
    }

    private Report(Builder builder) {
        this.targetBoardId = builder.targetBoardId;
        this.targetBoardTitle = builder.targetBoardTitle;
        this.reporterUserId = builder.reporterUserId;
        this.reporterUsername = builder.reporterUsername;
        this.reason = builder.reason;
    }

    // Static Builder class
    public static class Builder {
        private int targetBoardId;
        private String targetBoardTitle;
        private int reporterUserId;
        private String reporterUsername;
        private String reason;

        public Builder targetBoardId(int targetBoardId) {
            this.targetBoardId = targetBoardId;
            return this;
        }

        public Builder targetBoardTitle(String targetBoardTitle) { // 추가된 메서드
            this.targetBoardTitle = targetBoardTitle;
            return this;
        }

        public Builder reporterUserId(int reporterUserId) {
            this.reporterUserId = reporterUserId;
            return this;
        }

        public Builder reporterUsername(String reporterUsername) { // 추가된 메서드
            this.reporterUsername = reporterUsername;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Report build() {
            return new Report(this);
        }
    }
}
