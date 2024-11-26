package com.project.farmhelper.WorkLog;

import com.project.farmhelper.Entity.Crop;
import com.project.farmhelper.Crop.CropRepository;
import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.Entity.User;
import com.project.farmhelper.OpenAI.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/work/recommend")
public class WorkRecommendationController {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/create")
    public ResponseEntity<?> recommendWork(@RequestBody Map<String, Object> requestData) {
        Long cropId;
        String userId;
        String workContent;

        // 입력 데이터 검증
        try {
            cropId = Long.valueOf(requestData.get("cropId").toString());
            userId = requestData.get("userId").toString();
            workContent = requestData.get("workContent").toString();
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

        // OpenAI 프롬프트 생성
        String prompt;
        if (workContent == null || workContent.isEmpty()) {
            prompt = String.format(
                    "나는 %s를 기르기 시작했습니다. 이 작물의 초기 단계에서 할 3가지 작업을 한국어로 추천해주세요.",
                    crop.getCropName()
            );
        } else {
            prompt = String.format(
                    "나는 %s를 기르고 있습니다. 내가 마지막으로 한 작업은 '%s'입니다. 이에 적합한 다음 작업 3가지를 한국어로 추천해주세요.",
                    crop.getCropName(),
                    workContent
            );
        }

        // OpenAI를 통해 작업 추천 받기
        List<String> recommendations;
        try {
            recommendations = openAIService.getRecommendations(crop.getCropName(), workContent);
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