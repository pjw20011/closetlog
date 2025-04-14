package com.jw.fashionreview.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject; // 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    private String writer; //작성자

    private LocalDateTime createdDate = LocalDateTime.now(); // 작성일

    private Long viewCount = 0L; // 조회수
}
