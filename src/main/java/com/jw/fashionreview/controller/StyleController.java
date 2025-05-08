package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.dto.StyleRecommendationDto;
import com.jw.fashionreview.service.ClothesService;
import com.jw.fashionreview.service.StyleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StyleController {

    private final ClothesService clothesService;
    private final StyleService styleService;

    public StyleController(ClothesService clothesService, StyleService styleService) {
        this.clothesService = clothesService;
        this.styleService = styleService;
    }

    @GetMapping("/style")
    public String recommendStyles(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // 로그인한 사용자 정보 가져오기
        String username = userDetails.getUsername();
        User user = clothesService.findUserByUsername(username);

        // 사용자의 옷 불러오기
        List<Clothes> myClothes = clothesService.findByUser(user);

        // 추천 서비스 호출
        List<StyleRecommendationDto> recommendations = styleService.recommendStyles(username);

        // 뷰에 전달
        model.addAttribute("recommendations", recommendations);
        return "style";
    }
}
