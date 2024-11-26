package com.project.farmhelper.WorkLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// 도메인 엔티티 클래스
import com.project.farmhelper.Entity.Crop;
import com.project.farmhelper.Entity.User;
import com.project.farmhelper.Entity.WorkLog;

// JPA Repository 인터페이스
import com.project.farmhelper.Crop.CropRepository;
import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.WorkLog.WorkLogRepository;

import com.project.farmhelper.weather.WeatherService;

@RestController
@RequestMapping("/api/work")
public class WorkLogController {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private WeatherService weatherService;

    // 특정 Crop에 대한 WorkLog 조회 API
    @PostMapping("/worklist")
    public ResponseEntity<?> getWorkLogs(@RequestBody Map<String, Object> requestData) {
        // 요청 데이터에서 cropId, userId, location 추출
        Long cropId;
        String userId;
        String location;

        try {
            cropId = Long.valueOf(requestData.get("cropId").toString());
            userId = requestData.get("userId").toString();
            location = requestData.get("location").toString();  // location 추가
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request data");
        }

        // 1. Crop과 User 정보를 가져오기
        Optional<Crop> optionalCrop = cropRepository.findById(cropId);
        if (optionalCrop.isEmpty()) {
            return ResponseEntity.badRequest().body("Crop not found");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Crop crop = optionalCrop.get();
        User user = optionalUser.get();

        // 2. 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 3. 해당 Crop과 User, 날짜로 작업 로그 검색
        List<WorkLog> workLogs = workLogRepository.findByCropAndUserAndWorkDate(crop, user, today);

        // 4. 날씨 정보 가져오기 (location 포함)
        Map<String, Object> weatherData = weatherService.getTodayWeather(location);

        // 5. 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("cropName", crop.getCropName());
        response.put("nickname", user.getNickname());
        response.put("today", today);
        response.put("weather", weatherData); // 날씨 정보 추가

        if (workLogs.isEmpty()) {
            // 작업 로그가 없는 경우 빈 리스트 반환
            response.put("workLogs", List.of());
        } else {
            // 작업 로그가 있는 경우 상세 정보 반환
            List<Map<String, Object>> workLogDetails = workLogs.stream().map(workLog -> {
                Map<String, Object> workLogData = new HashMap<>();
                workLogData.put("workId", workLog.getWorkId());
                workLogData.put("workDate", workLog.getWorkDate());
                workLogData.put("workContent", workLog.getWorkContent());
                workLogData.put("workWeather", workLog.getWorkWeather());
                workLogData.put("workTemperature", workLog.getWorkTemperature());
                return workLogData;
            }).collect(Collectors.toList());
            response.put("workLogs", workLogDetails);
        }

        return ResponseEntity.ok(response);
    }
}
