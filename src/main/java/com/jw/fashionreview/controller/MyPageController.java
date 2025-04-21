package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Board;
import com.jw.fashionreview.domain.Comment;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.BoardService;
import com.jw.fashionreview.service.CommentService;
import com.jw.fashionreview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    // 마이페이지 메인 - 모든 콘텐츠 데이터 전달
    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername(); // 로그인된 사용자 ID 또는 email

        // 1. 사용자 정보 가져오기
        Optional<User> loginUser = userService.findByUsername(username);
        String nickname = loginUser.get().getNickname();

        // 2. 내가 쓴 글
        List<Board> myPosts = boardService.findByWriter(username);

        // 3. 내가 쓴 댓글
        List<Comment> myComments = commentService.findByWriter(username);

        model.addAttribute("nickname", nickname);
        model.addAttribute("myPosts", myPosts);
        model.addAttribute("myComments", myComments);

        return "mypage";
    }

    // 회원정보 수정 처리
    @PostMapping("/mypage/update")
    public String updateUserInfo(@AuthenticationPrincipal UserDetails user,
                                 @RequestParam String nickname,
                                 @RequestParam(required = false) String password,
                                 RedirectAttributes redirectAttributes) {

        String username = user.getUsername();

        if (userService.isNicknameDuplicate(nickname)) {
            redirectAttributes.addFlashAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "redirect:/mypage";
        }

        userService.updateNicknameAndPassword(username, nickname, password); // 비밀번호는 선택적 변경
        redirectAttributes.addFlashAttribute("success", "회원정보가 성공적으로 변경되었습니다.");
        return "redirect:/mypage";
    }
}
