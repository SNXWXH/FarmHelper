package com.project.farmhelper.Workpage;

import com.google.gson.JsonObject;
import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.common.Repository.CropRepository;
import com.project.farmhelper.common.Repository.WorkLogRepository;
import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
import com.project.farmhelper.common.entity.WorkLog;
import com.project.farmhelper.main.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/work")
public class WorkPageController {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private WeatherService weatherService;

    private final WorkPageService workPageService;

    @Autowired
    public WorkPageController(WorkPageService workPageService) {
        this.workPageService = workPageService;
    }

    // 특정 Crop에 대한 WorkLog 조회 API
    @PostMapping("/crop/worklist")
    public ResponseEntity<?> getWorkLogs(@RequestBody Map<String, Object> requestData) {

        // 요청 데이터에서 cropId, userId, ipAddress 추출
        Long cropId;
        String userId;
        String ipAddress;

        try {
            cropId = Long.valueOf(requestData.get("cropId").toString());
            userId = requestData.get("userId").toString();
            ipAddress = requestData.get("ipAddress").toString();  // ipAddress 추가
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

    @PostMapping("/listcreate")
    public ResponseEntity<String> createWorkLog(@RequestBody WorkPageRequest workLogRequest) {
        JsonObject json = new JsonObject();
        try {
            workPageService.createWorkLog(workLogRequest);
            json.addProperty("isOK", true);

            return ResponseEntity.ok(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            json.addProperty("isOK", false);

            return ResponseEntity.badRequest().body(json.toString());
        }
    }

    @PatchMapping("/patch")
    public ResponseEntity<String> updateWorkLog(@RequestBody WorkPageRequest workRequest) {
        boolean updated = workPageService.updateWorkLog(workRequest);

            JsonObject json = new JsonObject();
        if (updated) {
            json.addProperty("isOK", true);

            return ResponseEntity.ok(json.toString());
        } else {
            json.addProperty("isOK", false);

            return ResponseEntity.badRequest().body(json.toString());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWorkLog(@RequestBody WorkPageRequest workRequest) {
        boolean deleted = workPageService.deleteWorkLog(workRequest);

            JsonObject json = new JsonObject();
        if (deleted) {
            json.addProperty("isOK", true);

            return ResponseEntity.ok(json.toString());
        } else {
            json.addProperty("isOK", false);

            return ResponseEntity.badRequest().body(json.toString());
        }
    }

}
