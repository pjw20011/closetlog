package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class Usercontroller {

    private final UserServiceImpl userService;


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, HttpSession session, Model model) {
        String authStatus = (String) session.getAttribute("authStatus");

        if (!"verified".equals(authStatus)) {
            model.addAttribute("error", "이메일 인증을 완료해주세요.");
            return "register"; // 다시 회원가입 페이지로
        }

        userService.register(user);

        // 가입 완료 후 세션 정리
        session.removeAttribute("authCode");
        session.removeAttribute("authStatus");


        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "/login";
    }


}
