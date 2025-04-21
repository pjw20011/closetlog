package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.domain.Comment;
import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findByBoardId(Long boardId);

    void update(Comment comment);
    void delete(Long id);

    Comment findById(Long id);

    // 대댓글 저장
    void saveReply(Comment reply, Long parentId, String writer);


    List<Comment> findByWriter(String writer);

}
