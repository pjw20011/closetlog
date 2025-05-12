package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.dto.DayCell;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Long userId = 1L;  // TODO: 로그인 사용자로 교체
        List<DailyLook> dailyLooks = dailyLookService.getMyDailyLooks(userId);

        Map<LocalDate, DailyLook> lookMap = dailyLooks.stream()
                .collect(Collectors.toMap(DailyLook::getCreatedAt, d -> d));

        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

        List<List<DayCell>> calendar = new ArrayList<>();
        List<DayCell> week = new ArrayList<>();

        int dayOfWeekValue = firstDay.getDayOfWeek().getValue() % 7;  // 일=0, 월=1, ...
        for (int i = 0; i < dayOfWeekValue; i++) {
            week.add(null);  // 앞쪽 빈칸
        }

        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            DailyLook look = lookMap.get(date);
            week.add(new DayCell(date, look));
            if (week.size() == 7) {
                calendar.add(new ArrayList<>(week));
                week.clear();
            }
        }
        if (!week.isEmpty()) {
            while (week.size() < 7) {
                week.add(null);  // 뒤쪽 빈칸
            }
            calendar.add(week);
        }

        model.addAttribute("calendar", calendar);
        return "my_dailylooks";
    }

    @GetMapping("/my/dailylook/{id}")
    public String viewDailyLook(@PathVariable Long id, Model model) {
        DailyLook dailyLook = dailyLookService.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyLook not found"));
        model.addAttribute("dailyLook", dailyLook);
        return "my_dailylookview";
    }

    @PostMapping("/my/dailylook/update/{id}")
    public String updateDailyLook(
            @PathVariable Long id,
            @RequestParam String comment,
            @RequestParam boolean isPublic) {

        DailyLook dailyLook = dailyLookService.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));

        // TODO: 현재 로그인한 사용자 검증
        dailyLook.setComment(comment);
        dailyLook.setIsPublic(isPublic);

        dailyLookService.updateDailyLook(dailyLook);

        return "redirect:/my/dailylook/" + id;
    }

    @PostMapping("/my/dailylook/delete/{id}")
    public String deleteDailyLook(@PathVariable Long id) {
        dailyLookService.deleteDailyLook(id);
        return "redirect:/my";
    }

    @GetMapping("/dailylooks")
    public String getAllPublicDailyLooks(Model model) {
        List<DailyLook> publicLooks = dailyLookService.getPublicDailyLooks();
        model.addAttribute("dailyLooks", publicLooks);
        return "dailylook"; // dailylook.html
    }

    @GetMapping("/dailylook/{id}")
    public String viewPublicDailyLook(@PathVariable Long id, Model model) {
        DailyLook dailyLook = dailyLookService.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyLook not found"));
        model.addAttribute("dailyLook", dailyLook);
        return "dailylookview"; // dailylookview.html
    }







}


