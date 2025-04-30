package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ClothesController {

    private final ClothesService clothesService;

    @GetMapping("/plusclothes")
    public String plusclothesForm(Model model) {
        model.addAttribute("clothes", new Clothes());
        return "/plusclothes";
    }

    @PostMapping("/plusclothes")
    public String plusclothes(@RequestParam("imageFile") MultipartFile imageFile, Clothes clothes,
                              @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (!imageFile.isEmpty()) {
            // 저장 경로 설정 (변경)
            String uploadDir = "C:/Users/kisyj/Desktop/fashionReview/fashionreview/src/main/resources/static/clothes/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // 폴더 없으면 생성

            // 파일명 유니크하게 생성
            String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            String filepath = uploadDir + filename;

            // 파일 저장
            imageFile.transferTo(new File(filepath));

            // clothes에 경로 저장 (변경)
            clothes.setImagePath("/clothes/" + filename);
        }

        // 사용자 정보 clothes에 저장
        String username = userDetails.getUsername(); // username으로 사용자 조회
        User user = clothesService.findUserByUsername(username); // 아래 단계에서 추가할 메소드
        clothes.setUser(user);

        // TODO: 로그인한 사용자 정보 clothes.setUser() 연결 예정
        clothesService.save(clothes);

        return "redirect:/mycloset";
    }

    @GetMapping("/mycloset")
    public String mycloset(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = clothesService.findUserByUsername(username);
        List<Clothes> clothesList = clothesService.findByUserId(user.getId());

        model.addAttribute("clothesList", clothesList);
        model.addAttribute("topList", clothesService.findByCategory("상의"));
        model.addAttribute("bottomList", clothesService.findByCategory("하의"));
        model.addAttribute("outerList", clothesService.findByCategory("아우터"));
        model.addAttribute("etcList", clothesService.findByCategory("기타"));

        return "/mycloset";
    }

    @GetMapping("/viewclothes/{id}")
    public String viewClothes(@PathVariable String id, Model model) {
        Clothes clothes = clothesService.findById(id);
        model.addAttribute("clothes", clothes);
        return "/viewclothes";
    }


}
