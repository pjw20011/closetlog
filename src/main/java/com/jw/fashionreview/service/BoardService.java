package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoardService {
    // 게시물 저장
    void save(Board board);

    // 모든 게시물 조회
    List<Board> findAll();

    // 게시물 상세 조회
    Board findById(Long id);

    // 게시물 수정
    void update(Board board);

    Page<Board> findAll(Pageable pageable);

    // 게시물 삭제
    void delete(Long id);

    // 게시물 검색
    Page<Board> search(String type, String keyword, Pageable pageable);

}
