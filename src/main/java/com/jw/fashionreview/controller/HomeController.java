package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.service.ClothesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ClothesService clothesService;

    public HomeController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Clothes> clothesList = clothesService.findAll(); // 전체 옷 가져오기 (나중에 사용자별로)
        model.addAttribute("clothesList", clothesList);
        return "index";
    }
}

