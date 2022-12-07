package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortDto;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import com.example.team10searchengine.wiki.util.WikiConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikiService {

    private final WikiMapper wikiMapper;

    @Transactional
    @Cacheable(value = "wikiCache", cacheManager = "redisCacheManager")
    public List<?> searchWikiNgramSort(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikis;

        if(category.equals(WikiConstant.TOTAL)){
            wikis = wikiMapper.findByKeywordNgram(keyword);
        }else{
            wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
        }

        List<WikiSortDto> wikiSortDtos = getSortedWikis(wikis, keyword);

        log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
        return wikiSortDtos;
    }

    @Transactional
    @Cacheable(value = "wikiCache", cacheManager = "redisCacheManager")
    public List<?> searchWikiLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }

        List<WikiResDto> wikis;

        if(category.equals(WikiConstant.TOTAL)) {
            wikis = wikiMapper.findByKeywordLike(keyword + "%");
            log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
            return wikis;
        }

        wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
        log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);
        return wikis;
    }

    @Transactional
    public List<?> searchWikiOne(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikis;

        if(category.equals(WikiConstant.TOTAL)) {
            wikis = wikiMapper.findByKeywordOne(keyword);
            log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);
            return wikis;
        }

        wikis = wikiMapper.findByKeywordAndCategoryOne(keyword,category);
        log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);
        return wikis;
    }

    public List<WikiSortDto> getSortedWikis(List<WikiResDto> wikis, String keyword){
        List<WikiSortDto> wikiSortDtos = new ArrayList<>();

        String[] keywordTokens = keyword.split(" ");

        for(WikiResDto wiki : wikis){
            String noBlankKeyword = wiki.getKeyword().replace(" ","");

            int gain = keywordTokens.length;
            int score = 0;

            for(String word: keywordTokens){
                if(noBlankKeyword.contains(word)){
                    score += gain;
                }
                gain -= 1;
            }

            if(score > 0){
                WikiSortDto wikiSortDto = WikiSortDto.builder()
                        .id(wiki.getId())
                        .keyword(wiki.getKeyword())
                        .contents(wiki.getContents())
                        .img_url(wiki.getImg_url())
                        .detail_url(wiki.getDetail_url())
                        .classification(wiki.getClassification())
                        .score(score)
                        .build();

                wikiSortDtos.add(wikiSortDto);
            }

        }

        wikiSortDtos.sort(new ListComparator());

        return wikiSortDtos;
    }

}
