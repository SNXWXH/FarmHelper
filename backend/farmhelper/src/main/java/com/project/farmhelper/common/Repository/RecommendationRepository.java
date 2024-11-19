package com.project.farmhelper.common.Repository;

import com.project.farmhelper.common.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RecommendationRepository  extends JpaRepository<Recommendation, Long> {

    @Query("SELECT b FROM Recommendation b WHERE b.cropName = :cropName")
    List<Recommendation> findRecommendationCropName(@Param("cropName") String cropName);

    @Query("SELECT r.cropName FROM Recommendation r")
    List<String> findAllCropNames();

}
