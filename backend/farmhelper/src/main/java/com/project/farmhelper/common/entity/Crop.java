package com.project.farmhelper.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropId;  // 기본키

    private String cropName;    // 작물 이름
    private LocalDate cropDate; // 작물 심은 날짜
    private String imageUrl;    // 이미지 URL
    private boolean isClear = false;    // 완료 여부. 기본값 false

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)  // 외래키로 User의 uuid를 참조
    private User user;

}