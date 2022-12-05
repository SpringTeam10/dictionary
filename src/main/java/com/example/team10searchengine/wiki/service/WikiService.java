package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.entity.*;
import com.example.team10searchengine.wiki.repository.mongorepo.*;
import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import com.example.team10searchengine.wiki.util.WikiConstant;
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
    private final WikiMongoTotalRepo wikiMongoTotalRepo;
    private final WikiMongoCultureRepo wikiMongoCultureRepo;
    private final WikiMongoEtcRepo wikiMongoEtcRepo;
    private final WikiMongoHistoryRepo wikiMongoHistoryRepo;
    private final WikiMongoScienceRepo wikiMongoScienceRepo;
    private final WikiMongoSocialRepo wikiMongoSocialRepo;


    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiNgramSort(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikis;

        if (category.equals(WikiConstant.TOTAL)){
            WikiMongoTotal wikiMongoTotal = wikiMongoTotalRepo.findByKeyword(keyword);

            if(wikiMongoTotal == null){
                wikis = wikiMapper.findByKeywordNgram(keyword);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoTotal saveWikiMongoTotal = new WikiMongoTotal(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoTotalRepo.save(saveWikiMongoTotal);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoTotal.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SOCIAL)){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepo.findByKeyword(keyword);

            if(wikiMongoSocial == null){
                wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword, category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoSocialRepo.save(saveWikiMongoSocial);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }
        

        if(category.equals(WikiConstant.ETC)){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepo.findByKeyword(keyword);

            if(wikiMongoEtc == null){
                wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword, category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(keyword,wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoEtcRepo.save(saveWikiMongoEtc);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.CULTURE)){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepo.findByKeyword(keyword);

            if(wikiMongoCulture == null){
                wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoCultureRepo.save(saveWikiMongoCulture);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.HISTORY)){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepo.findByKeyword(keyword);

            if(wikiMongoHistory == null){
                wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoHistoryRepo.save(saveWikiMongoHistory);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SCIENCE)){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepo.findByKeyword(keyword);

            if(wikiMongoScience == null){
                wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoScienceRepo.save(saveWikiMongoScience);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail("404 Not Found","Category Doesn't Exist"), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        String initKeyword = keyword;

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }

        List<WikiSortResDto> wikis;

        if (category.equals(WikiConstant.TOTAL)){
            WikiMongoTotal wikiMongoTotal = wikiMongoTotalRepo.findByKeyword(initKeyword);

            if(wikiMongoTotal == null){
                wikis = wikiMapper.findByKeywordLike(keyword + "%");
                WikiMongoTotal saveWikiMongoTotal = new WikiMongoTotal(initKeyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoTotalRepo.save(saveWikiMongoTotal);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoTotal.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SOCIAL)){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepo.findByKeyword(initKeyword);

            if(wikiMongoSocial == null){
                wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(initKeyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoSocialRepo.save(saveWikiMongoSocial);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }


        if(category.equals(WikiConstant.ETC)){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepo.findByKeyword(initKeyword);

            if(wikiMongoEtc == null){
                wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(initKeyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoEtcRepo.save(saveWikiMongoEtc);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.CULTURE)){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepo.findByKeyword(initKeyword);

            if(wikiMongoCulture == null){
                wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(initKeyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoCultureRepo.save(saveWikiMongoCulture);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.HISTORY)){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepo.findByKeyword(initKeyword);

            if(wikiMongoHistory == null){
                wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(initKeyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoHistoryRepo.save(saveWikiMongoHistory);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SCIENCE)){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepo.findByKeyword(initKeyword);

            if(wikiMongoScience == null){
                wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(initKeyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoScienceRepo.save(saveWikiMongoScience);
                log.info("local millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            log.info("mongo millis : {}, keyword: {}, category: {}",System.currentTimeMillis() - init, keyword, category);
            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail("404 Not Found","Category Doesn't Exist"), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiOne(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikis;

        if(category.equals(WikiConstant.TOTAL)) {
            wikis = wikiMapper.findByKeywordOne(keyword);
            log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
            return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
        }

        wikis = wikiMapper.findByKeywordAndCategoryOne(keyword,category);
        log.info("keyword={}, category={}, ms={}", keyword, category, System.currentTimeMillis() - init);
        return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
    }

    public List<WikiSortResDto> getSortedWikiList(List<WikiResDto> wikis, String keyword){
        List<WikiSortResDto> wikiSortResDtos = new ArrayList<>();

        String[] keywordArr = keyword.split(" ");

        for(WikiResDto wiki : wikis){
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

                wikiSortResDtos.add(wikiSortResDto);
            }

        }

        wikiSortResDtos.sort(new ListComparator());

        return wikiSortResDtos;
    }

}
