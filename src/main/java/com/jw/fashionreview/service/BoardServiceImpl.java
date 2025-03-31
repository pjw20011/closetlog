package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Board findById(Long id){
        return boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. ID="+id));
    }

}
