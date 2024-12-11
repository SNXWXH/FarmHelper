package com.project.farmhelper.User;

import com.project.farmhelper.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // uuid로 사용자 조회
    User findByUserId(String userId);

    // uuid로 사용자 존재 여부 확인
    boolean existsByUserId(String userId);
}