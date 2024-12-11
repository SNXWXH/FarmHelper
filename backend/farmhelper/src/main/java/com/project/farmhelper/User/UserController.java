package com.project.farmhelper.User;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class UserController {

    @Autowired
    private UserService userService;

    // 사용자가 가입되었는지 확인
    @PostMapping
    public ResponseEntity<String> checkUserJoined(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        userId = userId.replace("\"", ""); // 불필요한 따옴표 제거

        boolean isUserJoined = userService.isUserJoined(userId);

        JsonObject json = new JsonObject();
        json.addProperty("isOk", isUserJoined);
        if (isUserJoined) {
            return ResponseEntity.ok(json.toString());
        } else {
            return ResponseEntity.badRequest().body(json.toString());
        }
    }

    // 사용자 저장
    @PostMapping("/nickname")
    public ResponseEntity<String> saveUser(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String nickname = request.get("nickname");

        boolean isUserJoined = userService.isUserJoined(userId);
        JsonObject json = new JsonObject();

        if (isUserJoined) {
            json.addProperty("isOK", false);
            return ResponseEntity.badRequest().body(json.toString());
        }

        boolean isSaved = userService.saveUser(userId, nickname);

        json.addProperty("isOk", isSaved);
        if (isSaved) {
            return ResponseEntity.ok(json.toString());
        } else {
            return ResponseEntity.badRequest().body(json.toString());
        }
    }
}
