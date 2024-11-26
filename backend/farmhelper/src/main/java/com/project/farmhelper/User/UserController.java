package com.project.farmhelper.Controller;

import com.project.farmhelper.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public boolean checkUserJoined(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");  // JSON에서 uuid 값을 가져옴
        userId = userId.replace("\"", "");  // 불필요한 따옴표가 있을 경우 제거

        System.out.println("Received userId: " + userId);  // 제대로 UUID가 출력되는지 확인
        return userService.isUserJoined(userId);  // UUID로 사용자 존재 여부 확인
    }

    // POST로 UUID와 nickname을 받아 사용자 저장
    @PostMapping("/api/login/nickname")
    public String saveUser(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String nickname = request.get("nickname");

        // 사용자 UUID가 이미 존재하는지 확인
        boolean isUserJoined = userService.isUserJoined(userId);

        if (isUserJoined) {
            return "User already exists"; // 이미 존재하는 사용자
        }

        // 사용자 저장
        boolean isSaved = userService.saveUser(userId, nickname);

        if (isSaved) {
            return "User saved successfully"; // 성공적으로 저장
        } else {
            return "Error occurred while saving user"; // 오류 발생
        }
    }
}