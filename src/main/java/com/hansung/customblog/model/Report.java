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

    private String targetUsername;

    private String reporterUsername;

    private String reason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected Report() {
    }

    private Report(Builder builder) {
        this.targetUsername = builder.targetUsername;
        this.reporterUsername = builder.reporterUsername;
        this.reason = builder.reason;
    }

    // Static Builder class
    public static class Builder {
        private String targetUsername;
        private String reporterUsername;
        private String reason;

        public Builder targetUsername(String targetUsername) {
            this.targetUsername = targetUsername;
            return this;
        }

        public Builder reporterUsername(String reporterUsername) {
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
