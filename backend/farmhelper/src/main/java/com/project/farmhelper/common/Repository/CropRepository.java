package com.project.farmhelper.common.Repository;

import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
import com.project.farmhelper.main.BestCropMonthDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropRepository  extends JpaRepository<Crop, Long> {

    @Query("SELECT new com.project.farmhelper.main.BestCropMonthDto(c.cropName, COUNT(c)) " +
            "FROM Crop c " +
            "WHERE YEAR(c.cropDate) = :year AND MONTH(c.cropDate) = :month " +
            "GROUP BY c.cropName " +
            "ORDER BY COUNT(c) DESC")
    List<BestCropMonthDto> findTopCropsByYearAndMonth(@Param("year") int year, @Param("month") int month);
}

