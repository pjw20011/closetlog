package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.ClothesService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ClothesService clothesService;

    public HomeController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Clothes> clothesList = new ArrayList<>();

        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = clothesService.findUserByUsername(username);
            clothesList = clothesService.findByUserId(user.getId());
        }

        model.addAttribute("clothesList", clothesList);
        return "index";
    }

}

