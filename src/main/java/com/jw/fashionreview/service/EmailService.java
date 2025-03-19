package com.jw.fashionreview.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    // 인증 코드 생성
    public String createCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000)); // 6자리 난수 생성
    }

    // 이메일 전송
    public void sendEmail(String toemail, HttpSession session) throws MessagingException {
        String code = createCode(); // 코드 생성
        session.setAttribute("authCode", code); // 세션에 저장


        // 이메일 발송
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);


        helper.setTo(toemail);
        helper.setSubject("패션리뷰 회원가입 인증 코드");
        helper.setText("인증 코드 : " + code, true);

        javaMailSender.send(message);
    }
}
