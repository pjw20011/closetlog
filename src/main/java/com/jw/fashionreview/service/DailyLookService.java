package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.repository.DailyLookLikeRepository;
import com.jw.fashionreview.repository.DailyLookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyLookService {


    private final DailyLookRepository dailyLookRepository;
    private final DailyLookLikeRepository dailyLookLikeRepository;

    public DailyLook createDailyLook(DailyLook dailyLook) {
        dailyLook.setCreatedAt(LocalDate.now());
        return dailyLookRepository.save(dailyLook);
    }

    public List<DailyLook> getMyDailyLooks(Long userId) {
        return dailyLookRepository.findByUserId(userId);
    }

    public List<DailyLook> getPublicDailyLooks() {
        return dailyLookRepository.findByIsPublicTrue();
    }

    @Transactional
    public void deleteDailyLook(Long id) {
        // ✅ 먼저 좋아요부터 삭제
        dailyLookLikeRepository.deleteByDailyLookId(id);

        // ✅ 그다음 데일리룩 삭제
        dailyLookRepository.deleteById(id);
    }

    public DailyLook updateDailyLook(DailyLook dailyLook) {
        return dailyLookRepository.save(dailyLook);
    }

    public Optional<DailyLook> findById(Long id) {
        return dailyLookRepository.findById(id);
    }

}
