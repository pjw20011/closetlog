package com.jw.fashionreview.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final ObjectMapper mapper = new ObjectMapper();   // JSON 처리용 Jackson 객체

    // 프롬프트 생성 메소드
    public String getCoordiRecommendation(String situation, List<String> clothes) throws IOException {
        String prompt = buildPrompt(situation, clothes); // 프롬프트 생성

        // HTTP Post 요청
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // API 키 설정


        Map<String, Object> message = Map.of(
                "role", "user", // 사용자 역할
                "content", prompt // 프롬프트 내용
        );
        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo", // 사용할 모델
                "messages", List.of(message) // 대화형태로 전달
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 4. 요청 보내고 응답 받기
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        // 5. 응답 JSON 파싱
        JsonNode root = mapper.readTree(response.getBody());
        return root.at("/choices/0/message/content").asText(); // 결과만 추출
    }

    // Gpt에게 전달할 프롬프트 생성 메소드
    private String buildPrompt(String situation, List<String> clothes) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음은 사용자의 옷장 목록입니다:\n");
        for (String item : clothes) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("\n상황: ").append(situation).append("\n");
        sb.append("이 옷들 중 어울리는 코디를 2가지 추천해 주세요. 각 코디는 간단한 설명도 포함해 주세요.");
        return sb.toString();
    }
}
