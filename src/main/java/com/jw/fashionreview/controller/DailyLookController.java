package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.service.DailyLookService;
import com.jw.fashionreview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DailyLookController {

    private final DailyLookService dailyLookService;
    private final UserService userService;

    @PostMapping("/api")
    public DailyLook create(@RequestBody DailyLook dailyLook) {
        return dailyLookService.createDailyLook(dailyLook);
    }

    @GetMapping("/my/{userId}")
    public List<DailyLook> getMyDailyLooks(@PathVariable Long userId) {
        return dailyLookService.getMyDailyLooks(userId);
    }

    @GetMapping("/public")
    public List<DailyLook> getPublicDailyLooks() {
        return dailyLookService.getPublicDailyLooks();
    }

    @PutMapping("/{id}")
    public DailyLook update(@PathVariable Long id, @RequestBody DailyLook dailyLook) {
        dailyLook.setId(id);
        return dailyLookService.updateDailyLook(dailyLook);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dailyLookService.deleteDailyLook(id);
    }

    @GetMapping("/form")
    public String dailyLookForm() {
        return "dailylook_form";
    }

    @PostMapping("/dailylook")
    public String createDailyLook(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("comment") String comment,
            @RequestParam("isPublic") boolean isPublic
    ) throws IOException {

        String fileName = imageFile.getOriginalFilename();
        String uploadDir = "C:/Users/kisyj/Desktop/fashionReview/fashionreview/src/main/resources/static/dailylook/";
        File destination = new File(uploadDir + fileName);

        // 파일 복사
        imageFile.transferTo(destination);

        String imageUrl = "/dailylook/" + fileName;

        User dummyUser = userService.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DailyLook dailyLook = DailyLook.builder()
                .user(dummyUser)
                .imageUrl(imageUrl)
                .comment(comment)
                .isPublic(isPublic)
                .createdAt(LocalDate.now())
                .build();

        dailyLookService.createDailyLook(dailyLook);

        return "redirect:/my";
    }

    @GetMapping("/my")
    public String getMyDailyLooks(Model model) {
        Long userId = 1L;  // TODO: 로그인한 사용자 ID로 대체
        List<DailyLook> dailyLooks = dailyLookService.getMyDailyLooks(userId);
        model.addAttribute("dailyLooks", dailyLooks);
        return "my_dailylooks";
    }



}


