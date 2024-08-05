package com.hansung.customblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "longtext") // 대용향 데이터
    private String content; // 섬머노트 라이브러리 사용

    private int count;

    @CreationTimestamp
    private Timestamp createDate;

    @ManyToOne // many = board, one = user
    @JoinColumn(name = "userId")
    private User user; // foreign key

    // fk 아님, 컬럼 생성 x, select 시 join을 통해 값만 얻어옴, mappedby에 fk이름 넣음, FetchType.LAZY로 필요시에만 가져온다는 뜻
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> reply;

    protected Board() {
    }

    public Board(int id, String title, String content, int count, Timestamp createDate, User user, List<Reply> reply) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.count = count;
        this.createDate = createDate;
        this.user = user;
        this.reply = reply;
    }

    private Board(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.count = builder.count;
        this.createDate = builder.createDate;
        this.user = builder.user;
        this.reply = builder.reply;
    }

    public static class Builder {
        private int id;
        private String title;
        private String content;
        private int count;
        private Timestamp createDate;
        private User user;
        private List<Reply> reply = new ArrayList<>();


        public Builder id(int id) {
            this.id = id;
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

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder createDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder reply(List<Reply> reply) {
            this.reply = reply;
            return this;
        }

        // 빌드 메서드
        public Board build() {
            return new Board(this);
        }
    }
}
