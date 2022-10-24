package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortDto;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
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

        List<WikiResDto> wikiList;

        if(category.equals("전체")){
            wikiList = wikiMapper.findByKeywordNgram(keyword);
        }else{
            wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
        }

        List<WikiSortDto> wikiSortDtoList = getSortedWikiList(wikiList, keyword);

        log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
        return wikiSortDtoList;
    }

    @Transactional
    @Cacheable(value = "wikiCache", cacheManager = "redisCacheManager")
    public List<?> searchWikiLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%") + "%";
        }

        List<WikiResDto> wikiList;

        if(category.equals("전체")){
            wikiList = wikiMapper.findByKeywordLike(keyword + "%");
        }else {
            wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
        }

        log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);

        return wikiList;
    }

    public List<WikiSortDto> getSortedWikiList(List<WikiResDto> wikiList, String keyword){
        List<WikiSortDto> wikiSortDtoList = new ArrayList<>();

        String[] keywordArr = keyword.split(" ");

        for(WikiResDto wiki : wikiList){
            String noBlankKeyword = wiki.getKeyword().replace(" ","");

            int gain = keywordArr.length;
            int score = 0;

            for(String word: keywordArr){
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

                wikiSortDtoList.add(wikiSortDto);
            }

        }

        wikiSortDtoList.sort(new ListComparator());

        return wikiSortDtoList;
    }

}
