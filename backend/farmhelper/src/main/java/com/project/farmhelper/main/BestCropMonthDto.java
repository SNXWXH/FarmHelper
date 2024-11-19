package com.project.farmhelper.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor // DTO로 사용할 수 있도록 모든 필드를 받는 생성자를 추가합니다.
public class BestCropMonthDto {
    private String cropName;
    private Long count; // COUNT 결과를 받을 타입 (Long)으로 설정
}
