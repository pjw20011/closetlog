package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.domain.Comment;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.BoardService;
import com.jw.fashionreview.service.CommentService;
import com.jw.fashionreview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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
        if (principal == null) {
            throw new RuntimeException("로그인 필요");
        }

        String username = principal.getName();
        board.setWriter(username);

        // 닉네임도 저장
        Optional<User> user = userService.findByUsername(username);
        user.ifPresent(value -> board.setNickname(value.getNickname()));

        boardService.save(board);
        redirectAttributes.addFlashAttribute("message", "게시글이 등록되었습니다.");
        return "redirect:/list";
    }



    // 게시글 목록 조회
    @GetMapping("/list")
    public String boardList(Model model, @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC ) Pageable pageable,
                            @RequestParam(value = "type", defaultValue = "all") String type,
                            @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Page<Board> boardPage;
        List<Board> boards = boardService.findAll();

        if (keyword != null && !keyword.isEmpty()) {
            boardPage = boardService.search(type, keyword, pageable);
        } else {
            boardPage = boardService.findAll(pageable);
        }
        Map<Long, String> boardNicknames = new HashMap<>();


        for (Board board : boards) {
            userService.findByUsername(board.getWriter()).ifPresent(user ->
                    boardNicknames.put(board.getId(), user.getNickname())
            );
        }

        model.addAttribute("boardList", boardPage);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        return "list";
    }


    @GetMapping("/view")
    public String viewBoard(@RequestParam Long id, Model model, Principal principal) {
        Board board = boardService.findById(id);

        // 댓글 목록 가져올 때 대댓글(parent != null)을 제외한 top-level 댓글만 필터링
        List<Comment> allComments = commentService.findByBoardId(id);
        List<Comment> topLevelComments = allComments.stream()
                .filter(c -> c.getParent() == null)
                .collect(Collectors.toList());

        model.addAttribute("board", board);
        model.addAttribute("comments", topLevelComments); // 💡 대댓글은 제외된 댓글만 전달

        if (principal != null) {
            model.addAttribute("currentUser", principal.getName());
        }

        return "view";
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
