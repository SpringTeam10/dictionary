package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortDto;
import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import com.example.team10searchengine.wiki.util.wikiCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> searchWikiNgramSort(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikiList;

        if(category.equals(wikiCategory.total)){
            wikiList = wikiMapper.findByKeywordNgram(keyword);
        }else{
            wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
        }

        List<WikiSortDto> wikiSortDtoList = getSortedWikiList(wikiList, keyword);

        log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
        return new ResponseEntity<>(ResponseDto.success(wikiSortDtoList), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%") + "%";
        }

        List<WikiResDto> wikiList;

        if(category.equals(wikiCategory.total)){
            wikiList = wikiMapper.findByKeywordLike(keyword + "%");
        }else {
            wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
        }

        log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);

        return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiOne(String keyword, String category) {
        if(category.equals(wikiCategory.total)) {
            return new ResponseEntity<>(ResponseDto.success(wikiMapper.findByKeywordOne(keyword)), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseDto.success(wikiMapper.findByKeywordAndCategoryOne(keyword,category)), HttpStatus.OK);
    }


    public List<WikiSortDto> getSortedWikiList(List<WikiResDto> wikiList, String keyword){
        List<WikiSortDto> wikiSortResDtoList = new ArrayList<>();

        String[] keywordTokens = keyword.split(" ");

        for(WikiResDto wiki : wikiList){
            int gain = keywordTokens.length;
            int score = 0;
            String searchedKeyword = wiki.getKeyword();

            if(keyword.equals(searchedKeyword)){
                score += gain + 1;
            }

            String noBlankKeyword = searchedKeyword.replace(" ","");

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

                wikiSortResDtoList.add(wikiSortDto);
            }

        }

        wikiSortResDtoList.sort(new ListComparator());

        return wikiSortResDtoList;
    }


}
