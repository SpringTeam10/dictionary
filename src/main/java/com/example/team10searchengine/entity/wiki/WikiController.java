package com.example.team10searchengine.entity.wiki;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WikiController {

    private final WikiService wikiService;

    @GetMapping("/search-wiki")
    public List<Wiki> getWiki(@RequestParam String searchword) {return wikiService.searchWiki(searchword);}

    @GetMapping("/search-wiki-ngram-filtering")
    public List<Wiki> getWikiNgramFiltering(@RequestParam String searchword) {return wikiService.searchWikiNgramFiltering(searchword);}

    @GetMapping("/search-wiki-ngram-sortfilter")
    public List<WikiSortDto> getWikiNgramSortFilter(@RequestParam String searchword) {return wikiService.searchWikiNgramSortFilter(searchword);}

    @GetMapping("/search-wiki-btree")
    public List<Wiki> getWikiLikeToken(@RequestParam String searchword) {return wikiService.searchWikiLikeToken(searchword);}

//    @GetMapping("/wiki-fulltext-search")
//    public ResponseEntity<?> getWikiFullText(@RequestParam String keyword) {return naverService.searchByFullText(keyword);}
}
