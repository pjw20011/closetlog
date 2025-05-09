package com.jw.fashionreview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 게시판 이미지
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/kisyj/Desktop/fashionReview/fashionreview/uploads/");

        // 옷 등록 이미지
        registry.addResourceHandler("/clothes/**")
                .addResourceLocations("file:///C:/Users/kisyj/Desktop/fashionReview/fashionreview/src/main/resources/static/clothes/");
        // 데일리룩 이미지
        registry.addResourceHandler("/dailylook/**")
                .addResourceLocations("file:///C:/Users/kisyj/Desktop/fashionReview/fashionreview/src/main/resources/static/dailylook/");
    }
}
