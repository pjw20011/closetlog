package com.jw.fashionreview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 대댓글
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 대댓글 리스트
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    private String nickname;   // ✨ 닉네임 추가

}