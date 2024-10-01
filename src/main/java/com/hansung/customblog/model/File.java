package com.hansung.customblog.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private int fileSize;

    @Column(nullable = false)
    private String fileContentType;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @CreationTimestamp
    private Timestamp createDate;

    protected File() {
    }

    private File(Builder builder) {
        this.id = builder.id;
        this.fileName = builder.fileName;
        this.filePath = builder.filePath;
        this.fileSize = builder.fileSize;
        this.fileContentType = builder.fileContentType;
        this.board = builder.board;
        this.createDate = builder.createDate;
    }

    public static class Builder {
        private int id;
        private String fileName;
        private String filePath;
        private int fileSize;
        private String fileContentType;
        private Board board;
        private Timestamp createDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder fileSize(int fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Builder fileContentType(String fileContentType) {
            this.fileContentType = fileContentType;
            return this;
        }

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Builder createDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }

        public File build() {
            return new File(this);
        }
    }
}
