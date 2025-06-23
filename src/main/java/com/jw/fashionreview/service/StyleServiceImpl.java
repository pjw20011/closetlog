package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.dto.StyleRecommendationDto;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StyleServiceImpl implements StyleService {

    private final ClothesService clothesService;

    public StyleServiceImpl(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    private Map<String, List<String>> getColorMatchMap() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("화이트", Arrays.asList("진청", "연청", "베이지", "카키", "검정","그레이"));
        map.put("블랙", Arrays.asList("베이지", "회색", "레드", "화이트","카키","그레이"));
        map.put("네이비", Arrays.asList("화이트", "베이지", "연청", "카키", "검정"));
        map.put("그린", Arrays.asList("베이지", "연청", "화이트", "회색", "검정"));
        map.put("레드", Arrays.asList("검정", "화이트", "회색", "네이비"));
        map.put("핑크", Arrays.asList("진청", "연청", "베이지", "회색", "검정"));
        map.put("옐로우", Arrays.asList("진청", "연청", "베이지", "그린", "회색"));
        map.put("베이지", Arrays.asList("진청", "연청", "화이트", "네이비", "브라운"));
        map.put("브라운", Arrays.asList("화이트", "베이지", "네이비", "블랙", "연청"));
        map.put("라이트 블루", Arrays.asList("진청", "연청", "화이트", "회색", "네이비"));
        map.put("퍼플", Arrays.asList("그레이", "화이트", "블랙"));
        map.put("오렌지", Arrays.asList("카키", "브라운", "베이지", "화이트", "블랙"));
        map.put("그레이", Arrays.asList("블랙", "화이트", "네이비", "핑크"));
        return map;
    }

    @Override
    public List<StyleRecommendationDto> recommendStyles(String username) {
        User user = clothesService.findUserByUsername(username);
        List<Clothes> allClothes = clothesService.findByUser(user);

        List<Clothes> tops = allClothes.stream().filter(c -> "상의".equals(c.getCategory())).collect(Collectors.toList());
        List<Clothes> bottoms = allClothes.stream().filter(c -> "하의".equals(c.getCategory())).collect(Collectors.toList());
        List<Clothes> outers = allClothes.stream().filter(c -> "아우터".equals(c.getCategory())).collect(Collectors.toList());

        Map<String, List<String>> colorMap = getColorMatchMap();
        List<StyleRecommendationDto> recommendations = new ArrayList<>();

        for (Clothes top : tops) {
            List<Clothes> matchedBottoms = bottoms.stream()
                    .filter(b -> colorMap.getOrDefault(top.getColor(), Collections.emptyList()).contains(b.getColor()))
                    .collect(Collectors.toList());

            List<Clothes> matchedOuters = outers.stream()
                    .filter(o -> colorMap.getOrDefault(top.getColor(), Collections.emptyList()).contains(o.getColor()))
                    .collect(Collectors.toList());

            recommendations.add(new StyleRecommendationDto(top, matchedBottoms, matchedOuters));
        }

        return recommendations;
    }
}