package com.hansung.customblog.model;

import jakarta.persistence.*;
import lombok.Getter;

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

    protected Notice() {
    }

    public Notice(int id, NoticeType noticeType, String title, String content) {
        this.id = id;
        this.noticeType = noticeType;
        this.title = title;
        this.content = content;
    }

    private Notice(Builder builder) {
        this.id = builder.id;
        this.noticeType = builder.noticeType;
        this.title = builder.title;
        this.content = builder.content;
    }

    public static class Builder {
        private int id;
        private NoticeType noticeType;
        private String title;
        private String content;

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

        public Notice build() {
            return new Notice(this);
        }
    }
}
