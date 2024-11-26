package com.project.farmhelper.weather;

import com.project.farmhelper.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // location을 파라미터로 받아서 날씨 정보를 반환
    @PostMapping("/api/weather")
    public List<Map<String, Object>> getWeatherData(@RequestBody Map<String, String> requestBody) {
        String location = requestBody.get("location"); // JSON 본문에서 location 값 추출
        return weatherService.getWeeklyWeather(location);
    }
}