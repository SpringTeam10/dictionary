package com.example.team10searchengine.kordict.util;

import com.example.team10searchengine.kordict.dto.KorDictResponseDto;

import java.util.Comparator;

public class korListComparator implements Comparator<KorDictResponseDto> {

    @Override
    public int compare(KorDictResponseDto k1, KorDictResponseDto k2){

        int k1Score = k1.getScore();
        int k2Score = k2.getScore();

        return Integer.compare(k2Score, k1Score);

    }
}