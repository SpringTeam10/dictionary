package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.entity.*;
import com.example.team10searchengine.wiki.repository.mongorepo.*;
import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikiService {

    private final WikiMapper wikiMapper;
    private final WikiMongoAllRepository wikiMongoAllRepository;
    private final WikiMongoCultureRepository wikiMongoCultureRepository;
    private final WikiMongoEtcRepository wikiMongoEtcRepository;
    private final WikiMongoHistoryRepository wikiMongoHistoryRepository;
    private final WikiMongoScienceRepository wikiMongoScienceRepository;
    private final WikiMongoSocialRepository wikiMongoSocialRepository;


    @Transactional
    public ResponseEntity<?> searchWikiNgramSort(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikiList;

        if (category.equals("전체")){
            WikiMongoAll wikiMongoAll = wikiMongoAllRepository.findByKeyword(keyword);
            if(wikiMongoAll == null){
                wikiList = wikiMapper.findByKeywordNgram(keyword);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoAll saveWikiMongoAll = new WikiMongoAll(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoAllRepository.save(saveWikiMongoAll);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoAll.getData()), HttpStatus.OK);
        }

        if(category.equals("사회인문")){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepository.findByKeyword(keyword);
            if(wikiMongoSocial == null){
                wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoSocialRepository.save(saveWikiMongoSocial);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }
        

        if(category.equals("기타")){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepository.findByKeyword(keyword);
            if(wikiMongoEtc == null){
                wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoEtcRepository.save(saveWikiMongoEtc);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals("문화")){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepository.findByKeyword(keyword);
            if(wikiMongoCulture == null){
                wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoCultureRepository.save(saveWikiMongoCulture);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals("역사")){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepository.findByKeyword(keyword);
            if(wikiMongoHistory == null){
                wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoHistoryRepository.save(saveWikiMongoHistory);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals("과학")){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepository.findByKeyword(keyword);
            if(wikiMongoScience == null){
                wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoScienceRepository.save(saveWikiMongoScience);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail("404 Not Found","Category Doesn't Exist"), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> searchWikiLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%") + "%";
        }

        List<WikiResDto> wikiList;

        if(category.equals("전체")) {
            wikiList = wikiMapper.findByKeywordLike(keyword + "%");
            log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);
            return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
        }

        wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
        log.info("keyword={}, category={}, ms={}",keyword, category, System.currentTimeMillis() - init);
        return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getMongoCatAllLastData(){
        List<WikiMongoAll> wikiMongoAllList = wikiMongoAllRepository.findAll();
        return new ResponseEntity<>(ResponseDto.success(wikiMongoAllList.get(wikiMongoAllList.size()-1)), HttpStatus.OK);
    }

    public List<WikiSortResDto> getSortedWikiList(List<WikiResDto> wikiList, String keyword){
        List<WikiSortResDto> wikiSortResDtoList = new ArrayList<>();

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
                WikiSortResDto wikiSortResDto = WikiSortResDto.builder()
                        .id(wiki.getId())
                        .keyword(wiki.getKeyword())
                        .contents(wiki.getContents())
                        .img_url(wiki.getImg_url())
                        .detail_url(wiki.getDetail_url())
                        .classification(wiki.getClassification())
                        .score(score)
                        .build();

                wikiSortResDtoList.add(wikiSortResDto);
            }

        }

        wikiSortResDtoList.sort(new ListComparator());

        return wikiSortResDtoList;
    }

}
