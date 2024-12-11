package com.project.farmhelper.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class WeatherController {

    private final WeatherServiceWeekly weatherServiceWeekly;

    @GetMapping("/weather/weekly/{ipAddress}")
    public Map<String, Object> getWeatherByIP(@PathVariable String ipAddress) {
        return weatherServiceWeekly.getFlatWeeklyWeatherByIP(ipAddress);
    }

}
