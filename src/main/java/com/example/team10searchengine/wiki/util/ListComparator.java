package com.example.team10searchengine.wiki.util;

import com.example.team10searchengine.wiki.dto.WikiSortResDto;

import java.util.Comparator;

public class ListComparator implements Comparator<WikiSortResDto>{

    @Override
    public int compare(WikiSortResDto w1, WikiSortResDto w2){

        int w1Score = w1.getScore();
        int w2Score = w2.getScore();

        return Integer.compare(w2Score, w1Score);

    }
}
