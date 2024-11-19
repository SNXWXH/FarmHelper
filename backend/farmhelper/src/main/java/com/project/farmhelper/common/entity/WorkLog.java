package com.project.farmhelper.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workId;

    @Column(nullable = false)
    private String workName;

    @Column(nullable = false)
    private LocalDateTime workDate;

    @Column(nullable = false)
    private String workContent;

    @Column(nullable = false)
    private String workWeather;

    @Column(nullable = false)
    private Double workTemperature;

    @CreationTimestamp
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;
}
