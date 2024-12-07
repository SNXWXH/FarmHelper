package com.project.farmhelper.WorkLog;

import com.project.farmhelper.OpenAI.OpenAIService;
import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.common.Repository.CropRepository;
import com.project.farmhelper.common.Repository.WorkLogRepository;
import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
import com.project.farmhelper.common.entity.WorkLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/work/recommend")
public class WorkRecommendationController {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkLogRepository workLogRepository; // WorkLogRepository 추가

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/create")
    public ResponseEntity<?> recommendWork(@RequestBody Map<String, Object> requestData) {
        Long cropId;
        String userId;

        // 입력 데이터 검증
        try {
            cropId = Long.valueOf(requestData.get("cropId").toString());
            userId = requestData.get("userId").toString();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("잘못된 요청 데이터입니다.");
        }

        // 작물 ID 유효성 확인
        Optional<Crop> optionalCrop = cropRepository.findById(cropId);
        if (optionalCrop.isEmpty()) {
            return ResponseEntity.badRequest().body("작물을 찾을 수 없습니다.");
        }

        Crop crop = optionalCrop.get();

        // userId로 사용자 정보 조회
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }

        String nickname = user.getNickname(); // 사용자 닉네임 조회

        // WorkLog에서 workContent 조회
        WorkLog latestWorkLog = workLogRepository.findTopByUser_UserIdAndCrop_CropIdOrderByWorkDateDesc(userId, cropId);
        String workContent = (latestWorkLog != null) ? latestWorkLog.getWorkContent() : null;

        // OpenAI 프롬프트 생성
        String prompt;
        if (workContent == null || workContent.isEmpty()) {
            prompt = String.format(
                    "나는 %s를 재배하려고 합니다. 이 작물의 초기 단계에서 해야 할 작업 세가지를 한국어로 구체적으로 알려주세요. " +
                            "번호나 불필요한 문구 없이 각 작업 내용을 짧고 간결하게 나열해주세요. 또한 빈 값없이 배열에 잘 담아서 보내줘",
                    crop.getCropName()
            );
        } else {
            prompt = String.format(
                    "나는 %s를 재배하려고 합니다. 이 작물의 초기 단계에서 해야 할 작업 세가지을 한국어로 구체적으로 알려주세요. " +
                            "번호나 불필요한 문구 없이 각 작업 내용을 짧고 간결하게 나열해주세요. 또한 빈 값없이 배열에 잘 담아서 보내줘",
                    crop.getCropName(),
                    workContent
            );
        }

        // OpenAI를 통해 작업 추천 받기
        List<String> recommendations;
        try {
            recommendations = openAIService.getRecommendations(crop.getCropName(), workContent);
            // 빈 값이나 불필요한 데이터 제거
            recommendations = recommendations.stream()
                    .filter(Objects::nonNull) // null 값 제거
                    .map(String::trim) // 문자열 공백 제거
                    .filter(s -> !s.isEmpty()) // 빈 문자열 제거
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("추천 작업을 가져오는 데 실패했습니다: " + e.getMessage());
        }

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("cropName", crop.getCropName());
        response.put("recommendations", recommendations);
        response.put("nickname", nickname);

        return ResponseEntity.ok(response);
    }
}