package com.project.farmhelper.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainApiController {

    private final MainService MainService;

    private final WeatherService weatherService;

    @GetMapping("/main/weather/{ipAddress}")
    public Map<String, Object> getWeatherByIP(@PathVariable String ipAddress) {
        return weatherService.getCurrentWeatherByIP(ipAddress);
    }

    @GetMapping("/main/todaycrop")
    ResponseEntity<List<RecommendationContentDTO>> getTodayCrop() throws IOException {

        return MainService.getRecommendation();
    }

    @GetMapping("/main/todaycrop/detail/{id}")
    ResponseEntity<List<RecommendationContentDTO>> DetatilCrop(@PathVariable String id){
        return MainService.getDetailRecommendation(id);
    }

    @GetMapping("/main/bestcrop")
    ResponseEntity<List<BestCropMonthDto>> bestCrop(){
        return MainService.getBestMonthCrop();
    }

    @GetMapping("/main/usercrop/{uuid}")
    ResponseEntity<List<UserWorkLogDTO>> getEnjoyBoard(@PathVariable String uuid){
        return MainService.getUserWorkLog(uuid);
    }

}
