package com.jw.fashionreview.service;

import com.jw.fashionreview.domain.Clothes;
import com.jw.fashionreview.domain.User;
import com.jw.fashionreview.dto.StyleRecommendationDto;
import com.jw.fashionreview.repository.ClothesRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StyleServiceImpl implements StyleService {

    private final ClothesRepository clothesRepository;
    private final UserService userService;

    public StyleServiceImpl(ClothesRepository clothesRepository, UserService userService) {
        this.clothesRepository = clothesRepository;
        this.userService = userService;
    }

    @Override
    public List<StyleRecommendationDto> recommendStyles(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        List<Clothes> userClothes = clothesRepository.findByUser(user);

        List<Clothes> tops = new ArrayList<>();
        List<Clothes> bottoms = new ArrayList<>();

        for (Clothes c : userClothes) {
            if ("상의".equals(c.getCategory())) {
                tops.add(c);
            } else if ("하의".equals(c.getCategory())) {
                bottoms.add(c);
            }
        }

        List<StyleRecommendationDto> recommendations = new ArrayList<>();

        for (Clothes top : tops) {
            List<String> matchedColors = getRecommendedBottomColors(top.getColor());
            List<Clothes> matchedBottoms = new ArrayList<>();

            for (Clothes bottom : bottoms) {
                if (matchedColors.contains(bottom.getColor())) {
                    matchedBottoms.add(bottom);
                }
            }

            if (!matchedBottoms.isEmpty()) {
                recommendations.add(new StyleRecommendationDto(top, matchedBottoms));
            }
        }

        return recommendations;
    }

    private Map<String, List<String>> getColorMatchMap() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("화이트", Arrays.asList("진청", "연청", "베이지", "카키", "검정"));
        map.put("블랙", Arrays.asList("베이지", "회색", "레드", "화이트"));
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

    private List<String> getRecommendedBottomColors(String topColor) {
        Map<String, List<String>> colorMap = getColorMatchMap();
        return colorMap.getOrDefault(topColor, new ArrayList<>());
    }
}

