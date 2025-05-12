package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.DailyLook;
import com.jw.fashionreview.domain.DailyLookLike;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.repository.DailyLookLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DailyLookLikeService {

    private final DailyLookLikeRepository likeRepository;

    public void toggleLike(DailyLook dailyLook, User user) {
        likeRepository.findByDailyLookAndUser(dailyLook, user)
                .ifPresentOrElse(
                        likeRepository::delete,
                        () -> {
                            DailyLookLike like = DailyLookLike.builder()
                                    .dailyLook(dailyLook)
                                    .user(user)
                                    .likedAt(LocalDateTime.now())
                                    .build();
                            likeRepository.save(like);
                        });
    }

    public long getLikeCount(DailyLook dailyLook) {
        return likeRepository.countByDailyLook(dailyLook);
    }

    public boolean hasLiked(DailyLook dailyLook, User user) {
        return likeRepository.findByDailyLookAndUser(dailyLook, user).isPresent();
    }
}
