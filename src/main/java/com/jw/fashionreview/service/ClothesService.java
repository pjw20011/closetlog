package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.repository.ClothesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothesService {

    private final ClothesRepository clothesRepository;

    public Clothes save(Clothes clothes) {
        return clothesRepository.save(clothes);
    }

    public List<Clothes> findByUserId(Long userId) {
        return clothesRepository.findByUserId(userId);
    }

    public List<Clothes> findAll() {
        return clothesRepository.findAll();
    }

}
