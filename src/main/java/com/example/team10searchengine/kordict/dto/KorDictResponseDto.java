package com.example.team10searchengine.kordict.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KorDictResponseDto {
    private Long id;
    private String word;
    private String is_unique;
    private String pronunciation;
    private String part;
    private String meaning;
    private String example;
    private String field;
    private String proverb;
    private String idiom;
    private String classification;


}
