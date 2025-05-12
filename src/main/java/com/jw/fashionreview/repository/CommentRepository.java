package com.jw.fashionreview.repository;

import com.jw.fashionreview.domain.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long boardId);

    List<Comment> findByWriter(String writer);

    @Transactional
    void deleteByBoardId(Long boardId);

    List<Comment> findByDailyLookIdOrderByCreatedDateAsc(Long dailyLookId);

}
