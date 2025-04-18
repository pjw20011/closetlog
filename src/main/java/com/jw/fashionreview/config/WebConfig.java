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
        // 브라우저가 /uploads/ 요청하면 실제 디스크의 경로에서 파일을 찾아줌
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/kisyj/Desktop/fashionReview/fashionreview/uploads/");
    }
}
