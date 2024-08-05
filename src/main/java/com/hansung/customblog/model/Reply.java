package com.hansung.customblog.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

    protected Reply() {
    }

    public Reply(int id, String content, Board board, User user, Timestamp createDate) {
        this.id = id;
        this.content = content;
        this.board = board;
        this.user = user;
        this.createDate = createDate;
    }
}
