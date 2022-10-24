package com.example.team10searchengine.wiki.controller;

import com.example.team10searchengine.wiki.dto.RankResponseDto;
import com.example.team10searchengine.wiki.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class WikiController {

    private final WikiService wikiService;

    @GetMapping("/search/wiki")
    public ResponseEntity<?> getSortedWiki(@RequestParam String keyword, @RequestParam String category) {

        if(keyword.length() == 1 | Pattern.matches(".*[a-zA-Z].*",keyword)){
            return wikiService.searchWikiLikeToken(keyword, category);
        }
        return wikiService.searchWikiNgramSort(keyword, category);
    }

    @GetMapping("/search/wiki/ranking")
    public List<RankResponseDto> getWikiRankList() {
        return wikiService.getWikiRankList();
    }

}
