package com.jw.fashionreview.dto;

import com.jw.fashionreview.domain.Clothes;
import java.util.List;

public class StyleRecommendationDto {
    private Clothes top;
    private List<Clothes> recommendedBottoms;

    public StyleRecommendationDto(Clothes top, List<Clothes> recommendedBottoms) {
        this.top = top;
        this.recommendedBottoms = recommendedBottoms;
    }

    public Clothes getTop() {
        return top;
    }

    public List<Clothes> getRecommendedBottoms() {
        return recommendedBottoms;
    }

    public String getTopColor() {
        return top != null ? top.getColor() : null;
    }

}
