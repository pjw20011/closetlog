package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Comment;
import com.jw.fashionreview.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comment")
    public String saveComment(@ModelAttribute Comment comment, Principal principal) {
        comment.setWriter(principal.getName());
        commentService.save(comment);



        return "redirect:/view?id=" + comment.getBoard().getId();
    }

    // 댓글 수정 및 삭제
    @PostMapping("/comment/action")
    public String updateOrDeleteComment(@ModelAttribute Comment comment,
                                        @RequestParam String action,
                                        Principal principal,
                                        RedirectAttributes redirectAttributes) {

        // DB에서 기존 댓글 조회
        Comment originalComment = commentService.findById(comment.getId());

        // 작성자 본인만 수정/삭제 가능
        if (!originalComment.getWriter().equals(principal.getName())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if ("delete".equals(action)) {
            commentService.delete(comment.getId());
            redirectAttributes.addFlashAttribute("message", "댓글이 삭제되었습니다.");
        } else if ("update".equals(action)) {
            originalComment.setContent(comment.getContent());
            commentService.save(originalComment);
            redirectAttributes.addFlashAttribute("message", "댓글이 수정되었습니다.");
        }

        return "redirect:/view?id=" + originalComment.getBoard().getId();
    }

    // 대댓글 작성
    @PostMapping("/comment/reply")
    public String createReply(@ModelAttribute Comment reply, @RequestParam Long parentId, Principal principal) {
        commentService.saveReply(reply, parentId, principal.getName());
        return "redirect:/view?id=" + reply.getBoard().getId();
    }

    // 대댓글 수정 및 삭제
    @PostMapping("/comment/reply/action")
    public String commentReplyAction(@ModelAttribute Comment reply,
                                        @RequestParam String action,
                                        Principal principal,
                                        RedirectAttributes redirectAttributes) {

        // DB에서 기존 댓글 조회
        Comment originalReply = commentService.findById(reply.getId());

        // 작성자 본인만 수정/삭제 가능
        if (!originalReply.getWriter().equals(principal.getName())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if ("delete".equals(action)) {
            commentService.delete(reply.getId());
            redirectAttributes.addFlashAttribute("message", "대댓글이 삭제되었습니다.");
        } else if ("update".equals(action)) {
            originalReply.setContent(reply.getContent());
            commentService.save(originalReply);
            redirectAttributes.addFlashAttribute("message", "대댓글이 수정되었습니다.");
        }

        return "redirect:/view?id=" + originalReply.getBoard().getId();
    }
}
