package com.jw.fashionreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jw.fashionreview.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // JpaRepository 인터페이스를 상속받아 CRUD 메서드를 사용할 수 있음
}
