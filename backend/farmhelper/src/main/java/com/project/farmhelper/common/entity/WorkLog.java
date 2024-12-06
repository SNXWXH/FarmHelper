package com.project.farmhelper.common.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import com.project.farmhelper.common.entity.User;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workId;  // 기본키

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)  // User 엔티티와의 외래키 관계
    private User user;

    @ManyToOne
    @JoinColumn(name = "cropId", nullable = false)  // Crop 엔티티와의 외래키 관계
    private Crop crop;
    private LocalDate workDate;     // 작업 날짜
    @Lob
    @Column(columnDefinition = "TEXT")
    private String workContent;     // 작업 내용
    private String workWeather;     // 작업 당시 날씨
    private String workTemperature; // 작업 당시 온도
}

