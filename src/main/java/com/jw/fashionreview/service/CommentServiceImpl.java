package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Comment;
import com.jw.fashionreview.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void save(Comment comment) {
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
    }

    @Override
    public void saveReply(Comment reply, Long parentId, String writer) {
       reply.setWriter(writer);

       Comment parent = commentRepository.findById(parentId)
               .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다"));
         reply.setParent(parent);
         reply.setBoard(parent.getBoard());
         commentRepository.save(reply);
    }



}
