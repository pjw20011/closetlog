package com.jw.fashionreview.repository;

import com.jw.fashionreview.domain.DailyLook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DailyLookRepository extends JpaRepository<DailyLook, Long> {

    List<DailyLook> findByUserId(Long userId);

    List<DailyLook> findByIsPublicTrue();
}
