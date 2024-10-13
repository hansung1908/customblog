package com.hansung.customblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db의 auto_increment 기능, 객체 생성시 자동으로 값 저장
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "longtext") // 대용량 데이터
    private String content; // 섬머노트 라이브러리 사용

    private int count;

    @CreationTimestamp // 현재 시간, 객체 생성시 자동으로 값 저장
    private Timestamp createDate;

    @ManyToOne // many = board, one = user
    @JoinColumn(name = "userId")
    private User user; // foreign key

    // 이 관계는 Reply 테이블에 외래 키가 존재하지만, Board 테이블에는 컬럼이 생성되지 않음.
    // 조회 시, JOIN을 통해 연관된 Reply 데이터만 얻어옴.
    // mappedBy에는 Reply 엔티티에서 부모 Board를 참조하는 필드 이름이 들어감.
    // CascadeType.REMOVE는 부모 엔티티가 삭제되면 연관된 자식 엔티티도 함께 삭제됨.
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @OrderBy("id desc") // 자식 엔티티의 정렬 방식
    private List<Reply> reply;

    protected Board() {
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
    // toBuilder 메서드
    public Builder toBuilder() {
        return new Builder()
                .id(this.id) // 현재 객체의 ID를 설정
                .title(this.title)
                .content(this.content)
                .count(this.count)
                .createDate(this.createDate)
                .user(this.user)
                .reply(this.reply);
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
