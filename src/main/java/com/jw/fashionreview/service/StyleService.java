package com.jw.fashionreview.service;

import com.jw.fashionreview.dto.StyleRecommendationDto;
import java.util.List;

public interface StyleService {
    List<StyleRecommendationDto> recommendStyles(String username);
}