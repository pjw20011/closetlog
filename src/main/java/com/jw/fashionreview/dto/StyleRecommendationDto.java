package com.jw.fashionreview.dto;

import com.jw.fashionreview.domain.Clothes;
import java.util.List;

public class StyleRecommendationDto {
    private Clothes top;
    private Clothes outer; // 새로 추가
    private List<Clothes> recommendedBottoms;

    public StyleRecommendationDto(Clothes top, Clothes outer, List<Clothes> recommendedBottoms) {
        this.top = top;
        this.outer = outer;
        this.recommendedBottoms = recommendedBottoms;
    }

    public Clothes getTop() {
        return top;
    }

    public Clothes getOuter() {
        return outer;
    }

    public List<Clothes> getRecommendedBottoms() {
        return recommendedBottoms;
    }

    public String getTopColor() {
        return top != null ? top.getColor() : null;
    }

    public String getOuterColor() {
        return outer != null ? outer.getColor() : null;
    }
}
