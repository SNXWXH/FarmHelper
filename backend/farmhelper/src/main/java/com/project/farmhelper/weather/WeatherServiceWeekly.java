package com.project.farmhelper.weather;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherServiceWeekly {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    private final RestTemplate restTemplate;

    public WeatherServiceWeekly(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getWeeklyWeatherByIP(String ipAddress) {
        String ipApiUrl = String.format("http://ip-api.com/json/%s", ipAddress);

        try {
            // Fetch IP ipAddress details
            ResponseEntity<String> ipResponse = restTemplate.getForEntity(ipApiUrl, String.class);
            if (!ipResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to fetch IP ipAddress data: " + ipResponse.getStatusCode());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode ipData = objectMapper.readTree(ipResponse.getBody());

            if (ipData.path("status").asText().equals("fail")) {
                throw new RuntimeException("Failed to fetch ipAddress for IP: " + ipData.path("message").asText());
            }

            String regionName = ipData.path("regionName").asText("Unknown");
            double lat = ipData.path("lat").asDouble();
            double lon = ipData.path("lon").asDouble();

            // Fetch weather forecast details
            String forecastUrl = String.format("%s?lat=%s&lon=%s&appid=%s&units=metric&lang=kr",
                    weatherApiUrl, lat, lon, weatherApiKey);

            ResponseEntity<String> forecastResponse = restTemplate.getForEntity(forecastUrl, String.class);
            if (!forecastResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to fetch forecast data: " + forecastResponse.getStatusCode());
            }

            JsonNode forecastData = objectMapper.readTree(forecastResponse.getBody());
            JsonNode forecastList = forecastData.path("list");
            long timezoneOffset = forecastData.path("city").path("timezone").asLong(0);

            if (!forecastList.isArray() || forecastList.size() == 0) {
                throw new RuntimeException("No forecast data available.");
            }

            // Process forecast data for 5 days (3-hour intervals)
            Map<String, Map<String, Object>> weeklyForecast = new HashMap<>();
            weeklyForecast.put("region", Map.of("name", regionName));

            for (JsonNode forecast : forecastList) {
                long timestamp = forecast.path("dt").asLong();
                LocalDateTime forecastTime = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds((int) timezoneOffset)))
                        .toLocalDateTime();

                // Group forecasts by day
                String day = forecastTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                JsonNode main = forecast.path("main");
                JsonNode weatherArray = forecast.path("weather");
                JsonNode wind = forecast.path("wind");

                String description = weatherArray.isArray() && weatherArray.size() > 0
                        ? weatherArray.get(0).path("description").asText("정보 없음")
                        : "정보 없음";

                Map<String, Object> forecastDetails = new HashMap<>();
                forecastDetails.put("time", forecastTime.format(DateTimeFormatter.ofPattern("HH:mm")));
                forecastDetails.put("temperature", String.format("%.1f°", main.path("temp").asDouble(0.0)));
                forecastDetails.put("feels_like", String.format("%.1f°", main.path("feels_like").asDouble(0.0)));
                forecastDetails.put("humidity", String.format("%.0f%%", main.path("humidity").asDouble(0.0)));
                forecastDetails.put("wind_speed", String.format("%.1f m/s", wind.path("speed").asDouble(0.0)));
                forecastDetails.put("description", description);

                // Add forecast details to the corresponding day
                weeklyForecast.computeIfAbsent(day, k -> new HashMap<>());
                weeklyForecast.get(day).put((String) forecastDetails.get("time"), forecastDetails);
            }

            return Map.of("region", regionName, "forecast", weeklyForecast);

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", "Error fetching weather data: " + e.getMessage());
        }
    }
}