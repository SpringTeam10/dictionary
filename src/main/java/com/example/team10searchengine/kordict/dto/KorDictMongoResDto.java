package com.example.team10searchengine.kordict.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KorDictMongoResDto {
    private String _id;
    private String word;
}
