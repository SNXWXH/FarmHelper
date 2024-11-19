package com.project.farmhelper.common.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@ToString
@Getter
@Setter
// 인자 없는 생성자를 생성. 외부에서 생성을 제한하고 JPA에서만 사용하도록 설정
@NoArgsConstructor
// 모든 필드 값을 인자로 받는 생성자를 자동 생성
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropid;

    // 게시물의 제목을 입력한다.
    @Column(nullable = false)
    private String cropName;

    @Column(nullable = false)
    private String content;

    // 게시물에 들어갈 내용을 입력한다.
    @Column(nullable = false)
    private String imageUrl;
}
