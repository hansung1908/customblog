package com.hansung.customblog.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @CreationTimestamp
    private Timestamp createDate;

    protected Notice() {
    }

    public Notice(int id, NoticeType noticeType, String title, String content, Timestamp createDate) {
        this.id = id;
        this.noticeType = noticeType;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    private Notice(Builder builder) {
        this.id = builder.id;
        this.noticeType = builder.noticeType;
        this.title = builder.title;
        this.content = builder.content;
        this.createDate = builder.createDate;
    }

    public static class Builder {
        private int id;
        private NoticeType noticeType;
        private String title;
        private String content;
        private Timestamp createDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder noticeType(NoticeType noticeType) {
            this.noticeType = noticeType;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }

        public Notice build() {
            return new Notice(this);
        }
    }
}
