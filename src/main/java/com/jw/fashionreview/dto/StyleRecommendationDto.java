package com.jw.fashionreview.dto;

import com.jw.fashionreview.domain.Clothes;
import java.util.List;

public class StyleRecommendationDto {
    private Clothes top;
    private List<Clothes> recommendedBottoms;
    private List<Clothes> recommendedOuters; // ← 추가

    public StyleRecommendationDto(Clothes top, List<Clothes> recommendedBottoms, List<Clothes> recommendedOuters) {
        this.top = top;
        this.recommendedBottoms = recommendedBottoms;
        this.recommendedOuters = recommendedOuters;
    }

    public Clothes getTop() { return top; }
    public List<Clothes> getRecommendedBottoms() { return recommendedBottoms; }
    public List<Clothes> getRecommendedOuters() { return recommendedOuters; } // ← getter도 추가
}
