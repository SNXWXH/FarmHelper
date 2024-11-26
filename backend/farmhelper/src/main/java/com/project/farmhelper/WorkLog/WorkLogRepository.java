package com.project.farmhelper.WorkLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// 도메인 엔티티 클래스 (Crop, User, WorkLog)는 해당 위치에 따라 import 경로를 수정하세요.
import com.project.farmhelper.Entity.Crop;
import com.project.farmhelper.Entity.User;
import com.project.farmhelper.Entity.WorkLog;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, String> {
    List<WorkLog> findByCropAndUserAndWorkDate(Crop crop, User user, LocalDate workDate);
}
