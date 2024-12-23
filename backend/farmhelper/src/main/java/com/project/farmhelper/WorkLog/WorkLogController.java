package com.project.farmhelper.WorkLog;

import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.common.Repository.CropRepository;
import com.project.farmhelper.common.Repository.WorkLogRepository;
import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
import com.project.farmhelper.common.entity.WorkLog;
import com.project.farmhelper.main.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 특수문자를 제거하는 유틸리티 메서드
    private String cleanInput(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[^a-zA-Z0-9가-힣 ]", ""); // 알파벳, 숫자, 한글, 공백만 허용
    }

    @PostMapping("/worklist")
    public ResponseEntity<?> getWorkLogs(@RequestBody Map<String, Object> requestData) {
        Long cropId;
        String userId;
        String ipAddress;

        try {
            // 특수문자를 제거한 데이터를 사용
            cropId = Long.valueOf(cleanInput(requestData.get("cropId").toString()));
            userId = cleanInput(requestData.get("userId").toString());
            ipAddress = requestData.get("ipAddress").toString();
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
        List<WorkLog> workLogs = workLogRepository.findByCropAndUser(crop, user);

        // 4. 날씨 정보 가져오기 (ipAddress 포함)
        System.out.println(ipAddress + "asdasdasd");
        Map<String, Object> weatherData = weatherService.getCurrentWeatherByIP(ipAddress);

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