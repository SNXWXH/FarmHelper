package com.project.farmhelper.common.Repository;

import com.project.farmhelper.common.entity.Crop;
import com.project.farmhelper.common.entity.User;
import com.project.farmhelper.common.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    @Query(value = "SELECT c.crop_name AS cropName, w.work_content AS workContent " +
            "FROM work_log w " +
            "JOIN crop c ON w.crop_id = c.crop_id " +
            "WHERE w.user_id = :uuid", nativeQuery = true)
    List<Object[]> findWorkNameAndContentByUserUuid(@Param("uuid") String uuid);

    List<WorkLog> findByCropAndUser(Crop crop, User user);

    WorkLog findTopByUser_UserIdAndCrop_CropIdOrderByWorkDateDesc(String userId, Long cropId);
}
