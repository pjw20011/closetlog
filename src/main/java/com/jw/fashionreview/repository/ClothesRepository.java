package com.jw.fashionreview.repository;

import com.jw.fashionreview.domain.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClothesRepository extends JpaRepository<Clothes, Long> {
    List<Clothes> findByUserId(Long userId);
    List<Clothes> findByCategory(String category);
}

