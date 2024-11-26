package com.project.farmhelper.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropid;

    @Column(nullable = false)
    private String cropName;

    @CreationTimestamp
    private LocalDateTime cropDate;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Boolean isClear;
//
//    @ManyToOne
//    @JoinColumn(name = "user_uuid", nullable = false)
//    private User user;
}
