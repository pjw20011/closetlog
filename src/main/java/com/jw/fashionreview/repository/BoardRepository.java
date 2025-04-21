package com.jw.fashionreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jw.fashionreview.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // JpaRepository 인터페이스를 상속받아 CRUD 메서드를 사용할 수 있음
    Page<Board> findBySubjectContaining(String keyword, Pageable pageable);
    Page<Board> findByWriterContaining(String keyword, Pageable pageable);

    List<Board> findByWriter(String writer);

}
