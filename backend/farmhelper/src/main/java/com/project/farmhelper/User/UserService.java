package com.project.farmhelper.Service;

import com.project.farmhelper.User.UserRepository;
import com.project.farmhelper.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UUID로 사용자가 가입되었는지 확인
    public boolean isUserJoined(String userId) {
        return userRepository.existsByUserId(userId); // UUID로 사용자가 존재하는지 확인
    }

    // UUID로 사용자 조회 (선택적으로 사용)
    public User getUserByUuid(String userId) {
        return userRepository.findByUserId(userId); // UUID로 사용자 찾기
    }

    // 사용자 저장 (UUID와 nickname)
    public boolean saveUser(String userId, String nickname) {
        // 새 사용자 객체 생성
        User user = new User(userId, nickname);
        // 사용자 저장
        userRepository.save(user);
        return true; // 저장 성공
    }

}