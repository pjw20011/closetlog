package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.repository.DailyLookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyLookService {


    private final DailyLookRepository dailyLookRepository;

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

    public void deleteDailyLook(Long id) {
        dailyLookRepository.deleteById(id);
    }

    public DailyLook updateDailyLook(DailyLook dailyLook) {
        return dailyLookRepository.save(dailyLook);
    }
}
