package com.example.team10searchengine.kordict.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KorDictResponseDto {
    private Long id;
    private String word;
    private String pronunciation;
    private String meaning;
    private String example;
    private String classification;
    private int score;


}
