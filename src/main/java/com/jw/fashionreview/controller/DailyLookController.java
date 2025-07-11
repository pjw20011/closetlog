package com.jw.fashionreview.controller;

import com.jw.fashionreview.domain.Comment;
import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.dto.DayCell;
import com.jw.fashionreview.repository.CommentRepository;
import com.jw.fashionreview.service.DailyLookLikeService;
import com.jw.fashionreview.service.DailyLookService;
import com.jw.fashionreview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DailyLookController {

    private final DailyLookService dailyLookService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final DailyLookLikeService dailyLookLikeService;

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
            @RequestParam("isPublic") boolean isPublic,
            Principal principal // ✅ 현재 로그인 사용자 정보
    ) throws IOException {

        String fileName = imageFile.getOriginalFilename();
        String uploadDir = "C:/Users/kisyj/Desktop/fashionReview/fashionreview/src/main/resources/static/dailylook/";
        File destination = new File(uploadDir + fileName);
        imageFile.transferTo(destination);

        String imageUrl = "/dailylook/" + fileName;

        // ✅ principal에서 사용자 이름으로 조회
        User loginUser = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DailyLook dailyLook = DailyLook.builder()
                .user(loginUser) // ✅ 현재 로그인 사용자로 저장
                .imageUrl(imageUrl)
                .comment(comment)
                .isPublic(isPublic)
                .createdAt(LocalDate.now())
                .build();

        dailyLookService.createDailyLook(dailyLook);
        return "redirect:/my";
    }


    @GetMapping("/my")
    public String getMyDailyLooks(@RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer month,
                                  Model model, Principal principal) {

        // 현재 날짜 기준으로 기본 설정
        LocalDate today = LocalDate.now();
        int targetYear = (year != null) ? year : today.getYear();
        int targetMonth = (month != null) ? month : today.getMonthValue();

        LocalDate firstDay = LocalDate.of(targetYear, targetMonth, 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<DailyLook> dailyLooks = dailyLookService.getMyDailyLooks(user.getId());

        Map<LocalDate, DailyLook> lookMap = dailyLooks.stream()
                .collect(Collectors.toMap(
                        DailyLook::getCreatedAt,
                        d -> d,
                        (d1, d2) -> d1
                ));

        List<List<DayCell>> calendar = new ArrayList<>();
        List<DayCell> week = new ArrayList<>();

        int dayOfWeekValue = firstDay.getDayOfWeek().getValue() % 7;
        for (int i = 0; i < dayOfWeekValue; i++) {
            week.add(null);
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
                week.add(null);
            }
            calendar.add(week);
        }

        // 이전/다음 달 계산
        LocalDate prevMonth = firstDay.minusMonths(1);
        LocalDate nextMonth = firstDay.plusMonths(1);

        model.addAttribute("calendar", calendar);
        model.addAttribute("currentYear", targetYear);
        model.addAttribute("currentMonth", targetMonth);
        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", prevMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());

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

    @GetMapping("/dailylooks") // ✅ 전체 목록은 복수형 경로로
    public String getAllPublicDailyLooks(Model model) {
        List<DailyLook> publicLooks = dailyLookService.getPublicDailyLooks();
        model.addAttribute("dailyLooks", publicLooks);
        return "dailylook"; // dailylook.html
    }

    @GetMapping("/dailylooks/view/{id}") // ✅ 상세보기는 /view 하위 경로로 이동
    public String viewPublicDailyLook(@PathVariable Long id, Model model, Principal principal) {
        DailyLook dailyLook = dailyLookService.findById(id).orElseThrow();
        List<Comment> comments = commentRepository.findByDailyLookIdOrderByCreatedDateAsc(id);

        long likeCount = dailyLookLikeService.getLikeCount(dailyLook);
        boolean hasLiked = false;

        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).orElseThrow();
            hasLiked = dailyLookLikeService.hasLiked(dailyLook, user);
        }

        model.addAttribute("dailyLook", dailyLook);
        model.addAttribute("comments", comments);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("hasLiked", hasLiked);

        return "dailylookview"; // dailylookview.html
    }

    @PostMapping("/dailylooks/view/{id}/comment") // ✅ 댓글도 상세 경로에 맞게
    public String postComment(@PathVariable Long id, @RequestParam String content, Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DailyLook dailyLook = dailyLookService.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyLook not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setWriter(user.getUsername());
        comment.setNickname(user.getNickname());
        comment.setDailyLook(dailyLook);
        comment.setCreatedDate(LocalDateTime.now());

        commentRepository.save(comment);

        return "redirect:/dailylooks/view/" + id;
    }

    @PostMapping("/dailylooks/view/{id}/like") // ✅ 좋아요도 상세 경로에 맞게
    public String toggleLike(@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        DailyLook dailyLook = dailyLookService.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyLook not found"));

        dailyLookLikeService.toggleLike(dailyLook, user);
        return "redirect:/dailylooks/view/" + id;
    }


}


