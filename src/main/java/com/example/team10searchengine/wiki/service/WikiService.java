package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.shared.RankResponseDto;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import com.example.team10searchengine.wiki.entity.*;
import com.example.team10searchengine.wiki.repository.mongorepo.*;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import com.example.team10searchengine.wiki.util.ListComparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final RedisTemplate<String, String> redisTemplate;


    @Transactional
    public ResponseEntity<?> searchWikiNgramSort(String keyword, String category) {
        updateScoreForRanking(keyword);

        if (category.equals("전체")){
            WikiMongoAll wikiMongoAll = wikiMongoAllRepository.findByKeyword(keyword);
            if(wikiMongoAll == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordNgram(keyword);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoAll saveWikiMongoAll = new WikiMongoAll(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoAllRepository.save(saveWikiMongoAll);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoAll.getData()), HttpStatus.OK);
        }

        if(category.equals("사회인문")){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepository.findByKeyword(keyword);
            if(wikiMongoSocial == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoSocialRepository.save(saveWikiMongoSocial);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }
        

        if(category.equals("기타")){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepository.findByKeyword(keyword);
            if(wikiMongoEtc == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoEtcRepository.save(saveWikiMongoEtc);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals("문화")){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepository.findByKeyword(keyword);
            if(wikiMongoCulture == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoCultureRepository.save(saveWikiMongoCulture);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals("역사")){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepository.findByKeyword(keyword);
            if(wikiMongoHistory == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoHistoryRepository.save(saveWikiMongoHistory);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals("과학")){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepository.findByKeyword(keyword);
            if(wikiMongoScience == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtoList = getSortedWikiList(wikiList, keyword);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(keyword,wikiSortResDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoScienceRepository.save(saveWikiMongoScience);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtoList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail("404 Not Found","Category Doesn't Exist"), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> searchWikiLikeToken(String keyword, String category) {
        String init_keyword = keyword;
        updateScoreForRanking(init_keyword);

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }

        if (category.equals("전체")){
            WikiMongoAll wikiMongoAll = wikiMongoAllRepository.findByKeyword(init_keyword);
            if(wikiMongoAll == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordLike(keyword + "%");
                WikiMongoAll saveWikiMongoAll = new WikiMongoAll(init_keyword,wikiList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoAllRepository.save(saveWikiMongoAll);
                return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoAll.getData()), HttpStatus.OK);
        }

        if(category.equals("사회인문")){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepository.findByKeyword(init_keyword);
            if(wikiMongoSocial == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(init_keyword,wikiList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoSocialRepository.save(saveWikiMongoSocial);
                return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }


        if(category.equals("기타")){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepository.findByKeyword(init_keyword);
            if(wikiMongoEtc == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(init_keyword,wikiList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoEtcRepository.save(saveWikiMongoEtc);
                return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals("문화")){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepository.findByKeyword(init_keyword);
            if(wikiMongoCulture == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(init_keyword,wikiList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoCultureRepository.save(saveWikiMongoCulture);
                return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals("역사")){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepository.findByKeyword(init_keyword);
            if(wikiMongoHistory == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(init_keyword,wikiList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoHistoryRepository.save(saveWikiMongoHistory);
                return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals("과학")){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepository.findByKeyword(init_keyword);
            if(wikiMongoScience == null){
                List<WikiResDto> wikiList = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(init_keyword,wikiList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
                wikiMongoScienceRepository.save(saveWikiMongoScience);
                return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail("404 Not Found","Category Doesn't Exist"), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> searchWikiOne(String keyword, String category) {
        updateScoreForRanking(keyword);
        if(category.equals("전체")) {
            return new ResponseEntity<>(ResponseDto.success(wikiMapper.findByKeywordOne(keyword)), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseDto.success(wikiMapper.findByKeywordAndCategoryOne(keyword,category)), HttpStatus.OK);
    }

    @Transactional
    public List<RankResponseDto> getWikiRankList(){
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores("ranking", 0, 9);
        return typedTuples.stream().map(RankResponseDto::convertToRankResponseDto).collect(Collectors.toList());
    }

    public void updateScoreForRanking(String keyword){
        double score = 0.0;
        try {
            redisTemplate.opsForZSet().incrementScore("ranking", keyword,1);
        } catch (Exception e) {
            log.info(e.toString());
        }
        redisTemplate.opsForZSet().incrementScore("ranking", keyword, score);
    }

    public List<WikiSortResDto> getSortedWikiList(List<WikiResDto> wikiList, String keyword){
        List<WikiSortResDto> wikiSortResDtoList = new ArrayList<>();

        String[] keywordArr = keyword.split(" ");

        for(WikiResDto wiki : wikiList){
            int gain = keywordArr.length;
            int score = 0;
            String searchedKeyword = wiki.getKeyword();

            if(keyword.equals(searchedKeyword)){
                score += gain + 1;
            }

            String noBlankKeyword = searchedKeyword.replace(" ","");

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
