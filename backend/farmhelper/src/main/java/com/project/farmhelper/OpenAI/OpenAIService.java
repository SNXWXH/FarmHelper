package com.project.farmhelper.OpenAI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getRecommendations(String cropName, String lastWorkContent) {
        String prompt = generatePrompt(cropName, lastWorkContent);

        // OpenAI 요청 JSON 생성
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4"); // 모델 이름 변경
        requestPayload.put("temperature", 0.7);
        requestPayload.put("max_tokens", 200); // 토큰 길이를 늘려서 더 상세한 답변 가능

        // messages 형식 구성
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a helpful farming assistant."));
        messages.add(Map.of("role", "user", "content", prompt));
        requestPayload.put("messages", messages);

        // HTTP 요청 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        try {
            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    OPENAI_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 응답 처리
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            // 모델의 추천 작업 추출
            List<String> recommendations = new ArrayList<>();
            String content = jsonResponse
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // 추천 작업을 줄 단위로 분리
            recommendations.addAll(Arrays.asList(content.split("\n")));
            return recommendations;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of("Failed to fetch recommendations: " + e.getMessage());
        }
    }

    // Prompt 생성 함수
    private String generatePrompt(String cropName, String lastWorkContent) {
        if (lastWorkContent == null || lastWorkContent.isEmpty()) {
            return String.format(
                    "농부가 %s을 (를) 기를 때 처음 해야 할 3가지 작업은 무엇인가요? 한국어로 답해주세요.",
                    cropName
            );
        } else {
            return String.format(
                    "작물 %s에 대한 마지막 작업은 \"%s\"였어요. 이를 기반으로 농부가 해야 할 다음 3가지 작업은 무엇인가요? 한국어로 답해주세요.",
                    cropName,
                    lastWorkContent
            );
        }
    }
}