package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 작성 폼 이동
    @GetMapping("/write")
    public String writeForm(Model model){
        model.addAttribute("board", new Board());
        return "write";
    }

    // 게시글 저장
    @PostMapping("/write")
    public String saveBoard(@ModelAttribute Board board){
        boardService.save(board);
        return "redirect:list";
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public String boardList(Model model){
        model.addAttribute("boardList", boardService.findAll());
        return "list";
    }

    // 게시글 상세 조회
    @GetMapping("/view")
    public String viewBoard(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "view"; // 여기서 "view"는 view.html을 의미
    }


}
