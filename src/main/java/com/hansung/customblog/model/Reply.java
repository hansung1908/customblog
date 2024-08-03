package com.hansung.customblog.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
public class Reply {

    protected Reply() {
    }

    public Reply(int id, String content, Board board, User user, Timestamp createDate) {
        this.id = id;
        Content = content;
        this.board = board;
        this.user = user;
        this.createDate = createDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String Content;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
