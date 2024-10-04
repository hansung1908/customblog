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

    private String targetBoardId;

    private String reporterUserId;

    private String reason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Report() {
    }

    private Report(Builder builder) {
        this.targetBoardId = builder.targetBoardId;
        this.reporterUserId = builder.reporterUserId;
        this.reason = builder.reason;
    }

    // Static Builder class
    public static class Builder {
        private String targetBoardId;
        private String reporterUserId;
        private String reason;

        public Builder targetBoardId(String targetBoardId) {
            this.targetBoardId = targetBoardId;
            return this;
        }

        public Builder reporterUserId(String reporterUserId) {
            this.reporterUserId = reporterUserId;
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
