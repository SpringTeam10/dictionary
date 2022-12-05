package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortDto;
import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import com.example.team10searchengine.wiki.util.WikiCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikiService {

    private final WikiMapper wikiMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "wikiCache")
    public ResponseEntity<?> searchWikiNgramSort(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikis;

        if(category.equals(WikiCategory.total)){
            wikis = wikiMapper.findByKeywordNgram(keyword);
        }else{
            wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
        }

        List<WikiSortDto> wikiSortDtos = getSortedWikis(wikis, keyword);

        log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
        return new ResponseEntity<>(ResponseDto.success(wikiSortDtos), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "wikiCache")
    public ResponseEntity<?> searchWikiLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%") + "%";
        }

        List<WikiResDto> wikis;

        if(category.equals(WikiCategory.total)) {
            wikis = wikiMapper.findByKeywordLike(keyword + "%");
            log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
            return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
        }

        wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
        log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);
        return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiOne(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikis;

        if(category.equals(WikiCategory.total)) {
            wikis = wikiMapper.findByKeywordOne(keyword);
            log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
            return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
        }

        wikis = wikiMapper.findByKeywordAndCategoryOne(keyword,category);
        log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
        return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
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
