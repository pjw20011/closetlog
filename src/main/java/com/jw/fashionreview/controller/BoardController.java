package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.domain.Comment;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.BoardService;
import com.jw.fashionreview.service.CommentService;
import com.jw.fashionreview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;
    private final CommentService commentService;

    // 게시글 작성 폼 이동
    @GetMapping("/write")
    public String writeForm(Model model){
        model.addAttribute("board", new Board());
        return "write";
    }

    @PostMapping("/write")
    public String saveBoard(@ModelAttribute Board board, Principal principal, RedirectAttributes redirectAttributes) {
        // 로그인한 사용자 ID 저장
        String username = principal.getName(); // ex: 'pjw20011'
        board.setWriter(username); // 저장은 ID 기준
        boardService.save(board);
        redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");
        return "redirect:/list";
    }


    // 게시글 목록 조회
    @GetMapping("/list")
    public String boardList(Model model){
        List<Board> boards = boardService.findAll();
        Map<Long, String> boardNicknames = new HashMap<>();

        for (Board board : boards) {
            userService.findByUsername(board.getWriter()).ifPresent(user ->
                    boardNicknames.put(board.getId(), user.getNickname())
            );
        }

        model.addAttribute("boardList", boards);
        model.addAttribute("boardNicknames", boardNicknames);
        return "list";
    }


    // 게시글 상세 조회
    @GetMapping("/view")
    public String viewBoard(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findById(id);
        List<Comment> comments = commentService.findByBoardId(id);

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        model.addAttribute("Comment", new Comment()); // 댓글 작성 폼을 위한 객체
        return "view"; // 여기서 "view"는 view.html을 의미
    }

    // 게시글 수정 및 삭제
    @PostMapping("/view")
    public String updateOrDelete(@ModelAttribute Board board,
                                 @RequestParam String action,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {

        // DB에서 기존 게시글 조회
        Board originalBoard = boardService.findById(board.getId());

        // 작성자 본인만 수정/삭제 가능
        if (!originalBoard.getWriter().equals(principal.getName())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if ("delete".equals(action)) {
            boardService.delete(board.getId());
            redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        } else if ("update".equals(action)) {
            originalBoard.setSubject(board.getSubject());
            originalBoard.setContent(board.getContent());
            boardService.save(originalBoard);
            redirectAttributes.addFlashAttribute("message", "게시글이 수정되었습니다.");
        }

        return "redirect:/list";
    }

}
