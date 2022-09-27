package com.example.team10searchengine.entity.kordict;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class KorDictRequestDto {
    private String word;
    private String type;
    private String isUnique;
    private String pronunciation;
    private String part;
    private String meaning;
    private String example;
    private String field;
    private String proverb;
    private String idiom;

}
