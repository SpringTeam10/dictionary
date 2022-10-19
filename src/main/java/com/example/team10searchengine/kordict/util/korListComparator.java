package com.example.team10searchengine.kordict.util;

import com.example.team10searchengine.kordict.dto.KorDictSortResponseDto;

import java.util.Comparator;

public class korListComparator implements Comparator<KorDictSortResponseDto> {

    @Override
    public int compare(KorDictSortResponseDto k1, KorDictSortResponseDto k2){

        int k1Score = k1.getScore();
        int k2Score = k2.getScore();

        return Integer.compare(k2Score, k1Score);

    }
}