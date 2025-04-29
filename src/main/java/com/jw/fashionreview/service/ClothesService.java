package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.repository.ClothesRepository;
import com.jw.fashionreview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;

    public Clothes save(Clothes clothes) {
        return clothesRepository.save(clothes);
    }

    public List<Clothes> findByUserId(Long userId) {
        return clothesRepository.findByUserId(userId);
    }

    public List<Clothes> findAll() {
        return clothesRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }


}
