package com.example.team10searchengine.kordict.util;

import com.example.team10searchengine.kordict.dto.KorDictSortResDto;

import java.util.Comparator;

public class korListComparator implements Comparator<KorDictSortResDto> {

    @Override
    public int compare(KorDictSortResDto k1, KorDictSortResDto k2){

        int k1Score = k1.getScore();
        int k2Score = k2.getScore();

        return Integer.compare(k2Score, k1Score);

    }
}