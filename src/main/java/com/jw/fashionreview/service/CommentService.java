package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Comment;
import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findByBoardId(Long boardId);
}
