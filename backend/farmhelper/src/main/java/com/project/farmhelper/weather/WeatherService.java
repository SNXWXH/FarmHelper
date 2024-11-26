package com.project.farmhelper.weather;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String weatherApiKey;
    private final RestTemplate restTemplate;
    private final String apiUrl = "https://api.openweathermap.org/data/2.5/forecast";

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getWeeklyWeather(String city) {
        // 도시 이름으로 5일간 날씨 데이터를 가져오기
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, weatherApiKey);
        String response;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (responseEntity.getStatusCodeValue() != 200) {
                throw new RuntimeException("Received HTTP " + responseEntity.getStatusCodeValue() + " from weather API.");
            }
            response = responseEntity.getBody();

            if (response == null || response.isEmpty()) {
                throw new RuntimeException("Received empty response from weather API.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(Map.of("error", "Error fetching weather data: " + e.getMessage()));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> weeklyWeather = new ArrayList<>();

        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            JsonNode dailyData = jsonResponse.path("list");

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            for (JsonNode dayData : dailyData) {
                long timestamp = dayData.path("dt").asLong();
                Instant instant = Instant.ofEpochSecond(timestamp);
                LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

                LocalDate date = dateTime.toLocalDate(); // 날짜 정보만 추출
                String hour = dateTime.format(timeFormatter); // 시간 정보만 추출

                double temperature = dayData.path("main").path("temp").asDouble();
                int humidity = dayData.path("main").path("humidity").asInt();
                String weatherCondition = dayData.path("weather").get(0).path("description").asText();

                // 강수량 정보 확인 및 추가
                double rain = 0.0; // 기본값
                if (dayData.has("rain") && dayData.path("rain").has("3h")) {
                    rain = dayData.path("rain").path("3h").asDouble();
                }

                Map<String, Object> dayWeather = new HashMap<>();
                dayWeather.put("date", date.toString()); // 날짜
                dayWeather.put("hour", hour); // 시간
                dayWeather.put("temperature", temperature);
                dayWeather.put("humidity", humidity);
                dayWeather.put("condition", weatherCondition);
                dayWeather.put("rain", rain); // 강수량 추가

                weeklyWeather.add(dayWeather);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(Map.of("error", "Error parsing weather data: " + e.getMessage()));
        }

        return weeklyWeather;
    }

    // 오늘의 날씨 정보만 가져오는 메서드 수정
    public Map<String, Object> getTodayWeather(String city) {
        // 도시 이름으로 오늘의 날씨 데이터를 가져오기
        List<Map<String, Object>> weeklyWeather = getWeeklyWeather(city);

        if (weeklyWeather.isEmpty() || weeklyWeather.get(0).containsKey("error")) {
            return Map.of("error", "Error fetching today's weather.");
        }

        // 오늘의 날씨 정보만 추출 (첫 번째 날 데이터가 오늘의 날씨에 해당)
        Map<String, Object> todayWeather = weeklyWeather.get(0);

        // 날씨와 온도만 포함한 응답 반환
        return Map.of(
                "weather", todayWeather.get("condition"),
                "temperature", todayWeather.get("temperature")
        );
    }

}