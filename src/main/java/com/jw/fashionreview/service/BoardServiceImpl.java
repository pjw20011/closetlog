package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

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
        return boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. ID="+id));
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
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }



}
