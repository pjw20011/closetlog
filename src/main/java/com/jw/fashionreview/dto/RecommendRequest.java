package com.jw.fashionreview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecommendRequest {
    private String situation;
    private String clothes;  // 쉼표 구분 문자열로 받기 (예: "청바지, 흰티")
}
