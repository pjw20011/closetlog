package com.jw.fashionreview.controller;

import com.jw.fashionreview.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-code")
    @ResponseBody
    public String sendCode(@RequestParam String email, HttpSession session) throws MessagingException {
        try {
            emailService.sendEmail(email, session);
            return "인증 코드 발송 완료";
        } catch(MessagingException e) {
            e.printStackTrace();
            return "인증 코드 발송 실패";
        }
    }

    @PostMapping("/verify-code")
    @ResponseBody
   public String verifyCode(@RequestParam String code, HttpSession session){
        String sessionCode = (String) session.getAttribute("authCode");

        if (sessionCode != null && sessionCode.equals(code)) {
            session.setAttribute("authStatus", "verified"); // 인증 완료
            return "인증 성공";
        }  else {
            return "인증 실패";
        }
    }
}

