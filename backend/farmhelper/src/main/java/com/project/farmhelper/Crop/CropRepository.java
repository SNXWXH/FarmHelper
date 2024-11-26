package com.project.farmhelper.Crop;

import com.project.farmhelper.Entity.Crop;
import com.project.farmhelper.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long> {

    // User를 기준으로 작업 일지(Crop) 리스트를 조회하는 메서드
    List<Crop> findByUser(User user);
}