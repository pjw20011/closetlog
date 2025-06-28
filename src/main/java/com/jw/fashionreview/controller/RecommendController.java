package com.jw.fashionreview.controller;

import com.jw.fashionreview.service.ChatGptService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class RecommendController {


    private final ChatGptService chatGptService;

    @GetMapping("/test")
    public String showTestPage() {
        return "test";  // templates/test.html 로 연결됨 (thymeleaf 방식)
    }

    public RecommendController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/api/recommend")
    public ResponseEntity<String> recommend(
            @RequestParam("situation") String situation,
            @RequestParam("clothes") String clothesRaw) {
        try {
            // 쉼표로 나눈 옷 목록을 리스트로 변환
            List<String> clothes = Arrays.stream(clothesRaw.split(","))
                    .map(String::trim)
                    .toList();

            String result = chatGptService.getCoordiRecommendation(situation, clothes);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("에러 발생: " + e.getMessage());
        }
    }
}


