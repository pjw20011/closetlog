package com.jw.fashionreview.repository;

import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.domain.DailyLookLike;
import com.jw.fashionreview.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyLookLikeRepository extends JpaRepository<DailyLookLike, Long> {
    Optional<DailyLookLike> findByDailyLookAndUser(DailyLook dailyLook, User user);
    long countByDailyLook(DailyLook dailyLook);
    void deleteByDailyLookAndUser(DailyLook dailyLook, User user);
}
