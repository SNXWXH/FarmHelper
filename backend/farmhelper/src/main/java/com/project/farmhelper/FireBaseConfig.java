package com.project.farmhelper;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;

public class FireBaseConfig {

    // application.yml(application.properties) 설정파일에 다운받은 비공개 키의 경로 추가
    @Value("${firebase.admin-key}")
    private String firebaseKeyPath;

    @PostConstruct
    public FirebaseApp firebaseApp() throws IOException {
        if(FirebaseApp.getApps().isEmpty()){
            FileInputStream fis = new FileInputStream(firebaseKeyPath);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(fis))
                    .build();

            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();

    }

}
