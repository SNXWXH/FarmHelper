package com.project.farmhelper.Crop;

import com.google.gson.JsonObject;
import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.common.Repository.CropRepository;
import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
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
@RequestMapping("/api")
public class CropController {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private UserRepository userRepository;

    // 작업일지 생성 API
    @PostMapping("/crop/create")
    public ResponseEntity<String> createWork(@RequestBody CropCreateRequest cropCreateRequest) {
        // 1. UUID를 통해 User를 찾아오기
        Optional<User> optionalUser = userRepository.findById(cropCreateRequest.getUserId()); // UserRepository에서 정확한 User 엔티티 사용
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = optionalUser.get();

        // 2. Crop 객체 생성
        Crop crop = new Crop();
        crop.setCropName(cropCreateRequest.getCropName());
        crop.setCropDate(LocalDate.parse(cropCreateRequest.getCropDate()));
        crop.setImageUrl(cropCreateRequest.getImageUrl());
        crop.setUser(user); // User와 연관 설정

        // isClear는 기본값이 false로 자동 설정됨
        // 3. Crop 객체를 DB에 저장
        cropRepository.save(crop);

        // 4. 성공 응답 반환
        JsonObject json = new JsonObject();
        json.addProperty("isOK", true);

        return ResponseEntity.ok(json.toString());
    }

    // 작업일지 목록 조회 API
    @PostMapping("/work/list")
    public ResponseEntity<?> getWorkList(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");

        // UUID를 통해 User를 찾아오기
        Optional<User> optionalUser = userRepository.findById(userId); // UserRepository에서 정확한 User 엔티티 사용
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = optionalUser.get();

        // 해당 User의 작업일지를 조회
        List<Crop> crops = cropRepository.findByUser(user);

        // 작업일지가 없으면 빈 목록 반환
        if (crops.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("nickname", user.getNickname());
            response.put("workList", List.of());
            return ResponseEntity.ok(response);
        }

        // 결과를 CropResponse 형태로 변환
        List<CropResponse> cropResponses = crops.stream()
                .map(crop -> new CropResponse(crop.getCropId(), crop.getCropName(), crop.getImageUrl(), crop.getCropDate()))
                .collect(Collectors.toList());

        // 최종 응답 생성
        Map<String, Object> response = new HashMap<>();
        response.put("nickname", user.getNickname());
        response.put("workList", cropResponses);

        return ResponseEntity.ok(response);
    }

    // CropCreateRequest DTO 클래스
    public static class CropCreateRequest {
        private String userId;
        private String cropName;
        private String cropDate; // yyyy-MM-dd 형식으로 받기
        private String imageUrl;

        // Getters and Setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCropName() {
            return cropName;
        }

        public void setCropName(String cropName) {
            this.cropName = cropName;
        }

        public String getCropDate() {
            return cropDate;
        }

        public void setCropDate(String cropDate) {
            this.cropDate = cropDate;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    // CropResponse DTO 클래스
    public static class CropResponse {
        private Long cropId;
        private String cropName;
        private String imageUrl;
        private LocalDate cropDate;

        // Constructor
        public CropResponse(Long cropId, String cropName, String imageUrl, LocalDate cropDate) {
            this.cropId = cropId;
            this.cropName = cropName;
            this.imageUrl = imageUrl;
            this.cropDate = cropDate;
        }

        // Getters and Setters
        public Long getCropId() {
            return cropId;
        }

        public void setCropId(Long cropId) {
            this.cropId = cropId;
        }

        public String getCropName() {
            return cropName;
        }

        public void setCropName(String cropName) {
            this.cropName = cropName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public LocalDate getCropDate() {
            return cropDate;
        }

        public void setCropDate(LocalDate cropDate) {
            this.cropDate = cropDate;
        }
    }
}