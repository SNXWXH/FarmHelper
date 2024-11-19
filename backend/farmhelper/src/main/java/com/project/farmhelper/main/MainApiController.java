package com.project.farmhelper.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainApiController {

    @Autowired
    private final MainService MainService;

//    @PostMapping("/api/weather")
//    public List<Map<String, Object>> getWeatherData(@RequestBody Map<String, String> requestBody) {
//        String location = requestBody.get("location"); // JSON 본문에서 location 값 추출
//        return MainService.getWeeklyWeather(location);
//    }


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

    @GetMapping("/main/todaycrop/usercrop/{uuid}")
    ResponseEntity<List<UserWorkLogDTO>> getEnjoyBoard(@PathVariable String uuid){
        return MainService.getUserWorkLog(uuid);
    }

}
