package com.example.team10searchengine.wiki.controller;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortDto;
import com.example.team10searchengine.wiki.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WikiController {

    private final WikiService wikiService;

    @GetMapping("/search/wiki-sort")
    public List<WikiSortDto> getSortedWiki(@RequestParam String keyword, @RequestParam String category) {
        return wikiService.searchWikiNgramSort(keyword, category);
    }

    @GetMapping("/search/wiki-one-en")
    public List<WikiResDto> getWiki(@RequestParam String keyword, @RequestParam String category) {
        return wikiService.searchWikiLikeToken(keyword, category);
    }

}
