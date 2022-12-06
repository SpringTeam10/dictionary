package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.shared.RankResponseDto;
import com.example.team10searchengine.wiki.constant.FailMsg;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import com.example.team10searchengine.wiki.entity.*;
import com.example.team10searchengine.wiki.repository.mongorepo.*;
import com.example.team10searchengine.wiki.repository.mybatisrepo.WikiMapper;
import com.example.team10searchengine.wiki.util.ListComparator;
import com.example.team10searchengine.wiki.constant.WikiConstant;
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
    private final WikiMongoTotalRepo wikiMongoTotalRepo;
    private final WikiMongoCultureRepo wikiMongoCultureRepo;
    private final WikiMongoEtcRepo wikiMongoEtcRepo;
    private final WikiMongoHistoryRepo wikiMongoHistoryRepo;
    private final WikiMongoScienceRepo wikiMongoScienceRepo;
    private final WikiMongoSocialRepo wikiMongoSocialRepo;
    private final RedisTemplate<String, String> redisTemplate;


    @Transactional
    public ResponseEntity<?> searchWikiNgramSort(String keyword, String category) {
        updateScoreForRanking(keyword);

        if (category.equals(WikiConstant.TOTAL)){
            WikiMongoTotal wikiMongoTotal = wikiMongoTotalRepo.findByKeyword(keyword);

            if(wikiMongoTotal == null){
                List<WikiResDto> wikis = wikiMapper.findByKeywordNgram(keyword);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoTotal saveWikiMongoTotal = new WikiMongoTotal(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoTotalRepo.save(saveWikiMongoTotal);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoTotal.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SOCIAL)){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepo.findByKeyword(keyword);

            if(wikiMongoSocial == null){
                List<WikiResDto> wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword, category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoSocialRepo.save(saveWikiMongoSocial);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }
        

        if(category.equals(WikiConstant.ETC)){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepo.findByKeyword(keyword);

            if(wikiMongoEtc == null){
                List<WikiResDto> wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword, category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoEtcRepo.save(saveWikiMongoEtc);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.CULTURE)){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepo.findByKeyword(keyword);

            if(wikiMongoCulture == null){
                List<WikiResDto> wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword, category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoCultureRepo.save(saveWikiMongoCulture);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.HISTORY)){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepo.findByKeyword(keyword);

            if(wikiMongoHistory == null){
                List<WikiResDto> wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword, category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(keyword, wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoHistoryRepo.save(saveWikiMongoHistory);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SCIENCE)){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepo.findByKeyword(keyword);

            if(wikiMongoScience == null){
                List<WikiResDto> wikis = wikiMapper.findByKeywordAndCategoryNgram(keyword,category);
                List<WikiSortResDto> wikiSortResDtos = getSortedWikiList(wikis, keyword);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(keyword,wikiSortResDtos, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoScienceRepo.save(saveWikiMongoScience);
                return new ResponseEntity<>(ResponseDto.success(wikiSortResDtos), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail(FailMsg.NOT_FOUND_CODE, FailMsg.CATEGORY_NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> searchWikiLikeToken(String keyword, String category) {
        String init_keyword = keyword;
        updateScoreForRanking(init_keyword);

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }

        if (category.equals(WikiConstant.TOTAL)){
            WikiMongoTotal wikiMongoTotal = wikiMongoTotalRepo.findByKeyword(init_keyword);

            if(wikiMongoTotal == null){
                List<WikiSortResDto> wikis = wikiMapper.findByKeywordLike(keyword + "%");
                WikiMongoTotal saveWikiMongoTotal = new WikiMongoTotal(init_keyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoTotalRepo.save(saveWikiMongoTotal);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResponseDto.success(wikiMongoTotal.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SOCIAL)){
            WikiMongoSocial wikiMongoSocial = wikiMongoSocialRepo.findByKeyword(init_keyword);

            if(wikiMongoSocial == null){
                List<WikiSortResDto> wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoSocial saveWikiMongoSocial = new WikiMongoSocial(init_keyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoSocialRepo.save(saveWikiMongoSocial);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoSocial.getData()), HttpStatus.OK);
        }


        if(category.equals(WikiConstant.ETC)){
            WikiMongoEtc wikiMongoEtc = wikiMongoEtcRepo.findByKeyword(init_keyword);

            if(wikiMongoEtc == null){
                List<WikiSortResDto> wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoEtc saveWikiMongoEtc = new WikiMongoEtc(init_keyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoEtcRepo.save(saveWikiMongoEtc);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoEtc.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.CULTURE)){
            WikiMongoCulture wikiMongoCulture = wikiMongoCultureRepo.findByKeyword(init_keyword);

            if(wikiMongoCulture == null){
                List<WikiSortResDto> wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoCulture saveWikiMongoCulture = new WikiMongoCulture(init_keyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoCultureRepo.save(saveWikiMongoCulture);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoCulture.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.HISTORY)){
            WikiMongoHistory wikiMongoHistory = wikiMongoHistoryRepo.findByKeyword(init_keyword);

            if(wikiMongoHistory == null){
                List<WikiSortResDto> wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoHistory saveWikiMongoHistory = new WikiMongoHistory(init_keyword, wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoHistoryRepo.save(saveWikiMongoHistory);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoHistory.getData()), HttpStatus.OK);
        }

        if(category.equals(WikiConstant.SCIENCE)){
            WikiMongoScience wikiMongoScience = wikiMongoScienceRepo.findByKeyword(init_keyword);

            if(wikiMongoScience == null){
                List<WikiSortResDto> wikis = wikiMapper.findByKeywordAndCategoryLike(keyword + "%", category);
                WikiMongoScience saveWikiMongoScience = new WikiMongoScience(init_keyword,wikis, LocalDateTime.now(ZoneId.of(WikiConstant.TIME_ZONE)));
                wikiMongoScienceRepo.save(saveWikiMongoScience);
                return new ResponseEntity<>(ResponseDto.success(wikis), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseDto.success(wikiMongoScience.getData()), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.fail(FailMsg.NOT_FOUND_CODE, FailMsg.CATEGORY_NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchWikiOne(String keyword, String category) {
        updateScoreForRanking(keyword);
        if(category.equals(WikiConstant.TOTAL)) {
            return new ResponseEntity<>(ResponseDto.success(wikiMapper.findByKeywordOne(keyword)), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseDto.success(wikiMapper.findByKeywordAndCategoryOne(keyword,category)), HttpStatus.OK);
    }

    @Transactional
    public List<RankResponseDto> getWikiRankList(){
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores(WikiConstant.RANKING_KEY, WikiConstant.RANKING_START, WikiConstant.RANKING_END);
        return typedTuples.stream().map(RankResponseDto::convertToRankResponseDto).collect(Collectors.toList());
    }

    public void updateScoreForRanking(String keyword){
        redisTemplate.opsForZSet().incrementScore(WikiConstant.RANKING_KEY, keyword,1);
    }

    public List<WikiSortResDto> getSortedWikiList(List<WikiResDto> wikis, String keyword){
        List<WikiSortResDto> wikiSortResDtos = new ArrayList<>();

        String[] keywordArr = keyword.split(" ");

        for(WikiResDto wiki : wikis){
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

                wikiSortResDtos.add(wikiSortResDto);
            }

        }

        wikiSortResDtos.sort(new ListComparator());

        return wikiSortResDtos;
    }

}
