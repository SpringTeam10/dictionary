package com.example.team10searchengine.kordict.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KorDictSortResponseDto {
    private Long id;
    private String word;
    private String pronunciation;
    private String meaning;
    private String example;
    private String classification;
    private int score;

    public KorDictSortResponseDto(Long id, String word, String pronunciation, String meaning, String example, String classification,int score){
        this.id = id;
        this.word = word;
        this.pronunciation = pronunciation;
        this.meaning = meaning;
        this.example = example;
        this.classification = classification;
        this.score = score;
    }
}
