package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClothesController {

    private final ClothesService clothesService;

    @GetMapping("/plusclothes")
    public String plusclothesForm(Model model) {
        model.addAttribute("clothes", new Clothes());
        return "clothes/plusclothes"; // templates/clothes/plusclothes.html
    }

    @PostMapping("/plusclothes")
    public String plusclothes(Clothes clothes, @AuthenticationPrincipal UserDetails userDetails) {
        // TODO: 로그인한 사용자 정보 연결 예정
        clothesService.save(clothes);
        return "redirect:/clothes/mycloset"; // 등록 후 내 옷장 화면으로 이동
    }
}
