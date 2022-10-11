package com.example.team10searchengine.wiki.controller;

import com.example.team10searchengine.wiki.service.WikiSearchSortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class WikiController {

    private final WikiSearchSortService wikiService;

//    @GetMapping("/search-wiki")
//    public List<WikiResDto> getWiki(@RequestParam String keyword) {return wikiService.searchWiki(keyword);}
//
//    @GetMapping("/search-wiki-bool")
//    public List<Wiki> getWikiBool(@RequestParam String keyword) {return wikiService.searchWikiBool(keyword);}
//
//    @GetMapping("/search-wiki-like")
//    public List<WikiResDto> getWikiLike(@RequestParam String keyword) {return wikiService.searchWikiLike(keyword);}
//
//    @GetMapping("/search-wiki-ngram-filtering")
//    public List<WikiResDto> getWikiNgramFiltering(@RequestParam String keyword) {return wikiService.searchWikiNgramFiltering(keyword);}
//
//    @GetMapping("/search-wiki-ngram-sortfilter")
//    public List<WikiSortDto> getWikiNgramSortFilter(@RequestParam String keyword) {return wikiService.searchWikiNgramSort(keyword);}
//
//    @GetMapping("/search-wiki-ngram-sort-cat")
//    public List<WikiSortDto> getWikiNgramSortFilter(@RequestParam String keyword, @RequestParam String category) {return wikiService.searchWikiNgramSortCat(keyword,category);}
//
//    @GetMapping("/search-wiki-btree")
//    public List<WikiResDto> getWikiLikeToken(@RequestParam String keyword) {return wikiService.searchWikiLikeToken(keyword);}

    @GetMapping("/search-wiki-sort")
    public ResponseEntity<?> getSortedWiki(@RequestParam String keyword) {
        if(keyword.length() == 1 | Pattern.matches(".*[a-zA-Z].*",keyword)){
            return wikiService.searchWikiLikeToken(keyword);}

        return wikiService.searchWikiNgramSort(keyword);
    }

    @GetMapping("/search-wiki-sort-cat")
    public ResponseEntity<?> getSortedWiki(@RequestParam String keyword, @RequestParam String category) {
        if(keyword.length() == 1 | Pattern.matches(".*[a-zA-Z].*",keyword)){
            return wikiService.searchWikiCatLikeToken(keyword, category);}

        return wikiService.searchWikiCatNgramSort(keyword, category);
    }

}
