package com.project.farmhelper.main;

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
public class WeatherService {

    private final RestTemplate restTemplate;
    @Value("${weather.api.key}")
    private String weatherApiKey;
    @Value("${weather.api.url}")
    private String weatherApiUrl;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getCurrentWeatherByIP(String ipAddress) {
        String ipApiUrl = String.format("http://ip-api.com/json/%s", ipAddress);
        System.out.println(ipApiUrl);
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

            // Find the forecast closest to the current time
            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
            JsonNode closestForecast = findClosestForecast(forecastList, now);

            if (closestForecast == null) {
                throw new RuntimeException("No suitable forecast data found.");
            }

            // Extract weather details
            long timestamp = closestForecast.path("dt").asLong();
            LocalDateTime forecastTime = Instant.ofEpochSecond(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            JsonNode main = closestForecast.path("main");
            JsonNode weatherArray = closestForecast.path("weather");
            JsonNode wind = closestForecast.path("wind");
            String description = "정보 없음";

            // Check for valid weather data
            if (weatherArray.isArray() && weatherArray.size() > 0) {
                description = weatherArray.get(0).path("description").asText("정보 없음");
            }

            // Extract general wind speed
            double windSpeed = wind.path("speed").asDouble(0.0);

            // Get sunrise time
            long sunriseUnix = forecastData.path("city").path("sunrise").asLong(0);
            String sunriseTime = formatUnixTime(sunriseUnix);

            Map<String, Object> forecastDetails = new HashMap<>();
            forecastDetails.put("region", regionName);
            forecastDetails.put("date", forecastTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            forecastDetails.put("sunrise", sunriseTime);
            forecastDetails.put("wind_speed", String.format("%.1f m/s", windSpeed));
            forecastDetails.put("temperature", String.format("%.1f°", main.path("temp").asDouble(0.0)));
            forecastDetails.put("feels_like", String.format("%.1f°", main.path("feels_like").asDouble(0.0)));
            forecastDetails.put("humidity", String.format("%.0f%%", main.path("humidity").asDouble(0.0)));
            forecastDetails.put("description", description);

            return forecastDetails;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return getCurrentWeatherByIP("125.209.222.141");
        }
    }

    private JsonNode findClosestForecast(JsonNode forecastList, LocalDateTime now) {
        JsonNode closestForecast = null;
        long smallestTimeDifference = Long.MAX_VALUE;

        // Convert ZoneId to ZoneOffset
        ZoneOffset currentZoneOffset = now.atZone(ZoneId.systemDefault()).getOffset();

        for (JsonNode forecast : forecastList) {
            long forecastTimestamp = forecast.path("dt").asLong();
            long timeDifference = Math.abs(now.toEpochSecond(currentZoneOffset) - forecastTimestamp);

            if (timeDifference < smallestTimeDifference) {
                smallestTimeDifference = timeDifference;
                closestForecast = forecast;
            }
        }

        return closestForecast;
    }

    private String formatUnixTime(long unixTime) {
        return DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochSecond(unixTime));
    }
}