package com.example.team10searchengine.wiki.util;

import com.example.team10searchengine.wiki.dto.WikiSortDto;

import java.util.Comparator;

public class ListComparator implements Comparator<WikiSortDto>{

    @Override
    public int compare(WikiSortDto w1, WikiSortDto w2){

        int w1Score = w1.getScore();
        int w2Score = w2.getScore();

        return Integer.compare(w2Score, w1Score);

    }
}
