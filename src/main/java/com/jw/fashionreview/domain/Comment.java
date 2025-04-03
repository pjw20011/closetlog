package com.jw.fashionreview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    private String content;

    // 댓글 작성자
    private String writer;

    // 작성 시간
    private LocalDateTime createdDate = LocalDateTime.now();

    // 게시글과의 연관 관계
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}