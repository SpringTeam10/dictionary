package com.example.team10searchengine.wiki;

import com.example.team10searchengine.wiki.dto.WikiSortDto;

import java.util.Comparator;

public class ListComparator implements Comparator<WikiSortDto>{

    @Override
    public int compare(WikiSortDto w1, WikiSortDto w2){

        int w1Num = w1.getNum();
        int w2Num = w2.getNum();

        return Integer.compare(w2Num, w1Num);

    }
}
