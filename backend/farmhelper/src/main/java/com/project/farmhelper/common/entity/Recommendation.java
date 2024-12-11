package com.project.farmhelper.common.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;  // 추천 작물의 기본키

    private String cropName;        // 추천 작물 이름

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;    // 추천 작물 설명
    private String imageUrl;        // 추천 작물 이미지 URL
}