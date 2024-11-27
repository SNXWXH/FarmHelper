package com.project.farmhelper.main;

import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.common.Prompt;
import com.project.farmhelper.common.Repository.CropRepository;
import com.project.farmhelper.common.Repository.RecommendationRepository;
import com.project.farmhelper.common.Repository.WorkLogRepository;
import com.project.farmhelper.common.entity.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final CropRepository cropRepository;
    private final WorkLogRepository workLogRepository;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final Prompt promptGpt;



    public ResponseEntity<List<RecommendationContentDTO>> getRecommendation() throws IOException {


        // Step 1: Fetch all crop names from the Recommendation table
        List<String> allCropNames = recommendationRepository.findAllCropNames();
        log.info("Fetched crop names: {}", allCropNames);

        // Step 2: Get recommended crop names from GPT
        String recommendedCropNames = promptGpt.getBestCropsFromGPT(allCropNames)
                .stream()
                .map(crop -> crop.replaceAll("^\\d+\\.\\s*", "")) // Clean names (remove leading numbers and periods)
                .collect(Collectors.joining(", ")); // Join names with a comma
        log.info("Recommended crop names from GPT: {}", recommendedCropNames);

        // Step 3: Split the recommended crop names into an array
        String[] crops = recommendedCropNames.split(",\\s*"); // Split by comma and optional spaces
        log.info("Split crops array: {}", Arrays.toString(crops));

        // Step 4: Fetch recommendations for each crop name
        List<Recommendation> recommendations = Arrays.stream(crops)
                .map(crop -> {
                    // Query for the crop name
                    List<Recommendation> cropRecommendations = recommendationRepository.findRecommendationCropName(crop);
                    if (cropRecommendations.isEmpty()) {
                        log.warn("No recommendations found for crop: {}", crop);
                    }
                    return cropRecommendations; // Return list
                })
                .flatMap(List::stream) // Flatten the lists into a single stream
                .collect(Collectors.toList()); // Collect into a single list
        log.info("Fetched recommendations: {}", recommendations);

        // Step 5: Convert recommendations to DTOs
        List<RecommendationContentDTO> dto = recommendations.stream()
                .map(recommendation -> {
                    // Map each recommendation to a DTO
                    RecommendationContentDTO recommendationDto = new RecommendationContentDTO();
                    recommendationDto.setCropName(recommendation.getCropName());
                    recommendationDto.setImageUrl(recommendation.getImageUrl());
                    return recommendationDto;
                })
                .collect(Collectors.toList());
        log.info("Converted to DTOs: {}", dto);

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<List<RecommendationContentDTO>> getDetailRecommendation(String id) {


        List<Recommendation> cropRecommendations = recommendationRepository.findRecommendationCropName(id);

        // Step 5: Convert recommendations to DTOs
        List<RecommendationContentDTO> dto = cropRecommendations.stream()
                .map(recommendation -> {
                    // Map each recommendation to a DTO
                    RecommendationContentDTO recommendationDto = new RecommendationContentDTO();
                    recommendationDto.setCropName(recommendation.getCropName());
                    recommendationDto.setImageUrl(recommendation.getImageUrl());
                    recommendationDto.setContent(recommendation.getDescription());
                    return recommendationDto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<List<BestCropMonthDto>> getBestMonthCrop() {
        LocalDate day = LocalDate.now();
        int year = day.getYear();
        int month = day.getMonthValue();

        // 이미 DTO로 반환되므로 추가 매핑 필요 없음
        List<BestCropMonthDto> dto = cropRepository.findTopCropsByYearAndMonth(year, month);

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<List<UserWorkLogDTO>> getUserWorkLog(String uuid){
        List<UserWorkLogDTO> dto = workLogRepository.findWorkNameAndContentByUserUuid(uuid);

        return ResponseEntity.ok(dto);
    }

}



