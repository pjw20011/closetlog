package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.repository.BoardRepository;
import com.jw.fashionreview.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    public void save(Board board){
        boardRepository.save(board);
    }

    @Override
    public List<Board> findAll(){
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID=" + id));

        board.setViewCount(board.getViewCount() + 1);
        boardRepository.save(board);

        return board;
    }

    @Override
    public void update(Board board) {
        Board existing = boardRepository.findById(board.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        existing.setSubject(board.getSubject());
        existing.setContent(board.getContent());
        boardRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long boardId) {
        // 댓글 먼저 삭제
        commentRepository.deleteByBoardId(boardId);

        // 그 다음 게시글 삭제
        boardRepository.deleteById(boardId);
    }


    @Override
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Override
    public Page<Board> search(String type, String keyword, Pageable pageable) {
        if (type.equals("subject")) {
            return boardRepository.findBySubjectContaining(keyword, pageable);
        } else if (type.equals("writer")) {
            return boardRepository.findByWriterContaining(keyword, pageable);
        } else {
            return boardRepository.findAll(pageable); // type == all
        }
    }

    @Override
    public List<Board> findByWriter(String username) {
        return boardRepository.findByWriter(username);
    }


}
