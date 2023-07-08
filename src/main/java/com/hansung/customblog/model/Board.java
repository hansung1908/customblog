package com.hansung.customblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "longtext") // 대용향 데이터
    private String Content; // 섬머노트 라이브러리 사용

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
}
