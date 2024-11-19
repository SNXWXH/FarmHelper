package com.project.farmhelper.common.Repository;

import com.project.farmhelper.common.entity.WorkLog;
import com.project.farmhelper.main.UserWorkLogDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    @Query("SELECT w.workName, w.workContent FROM WorkLog w WHERE w.user.uuid = :uuid")
    List<UserWorkLogDTO> findWorkNameAndContentByUserUuid(@Param("uuid") String uuid);

}
