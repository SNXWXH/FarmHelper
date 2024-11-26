package com.project.farmhelper.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor  // 인자 없는 생성자
@AllArgsConstructor // 모든 필드를 포함하는 생성자
public class User {

    @Id
    @Column(unique = true, name = "user_id")
    private String userId;

    private String nickname;

}