package com.example.team10searchengine.kordict.service;

import com.example.team10searchengine.kordict.dto.KorDictSortResDto;
import com.example.team10searchengine.kordict.entity.KorDict;
import com.example.team10searchengine.kordict.entity.KorDictMongo;
import com.example.team10searchengine.kordict.repository.mongorepo.KorDictMongoRepository;
import com.example.team10searchengine.kordict.repository.mybatisrepo.KorDictMapper;
import com.example.team10searchengine.kordict.util.korListComparator;
import com.example.team10searchengine.shared.RankResponseDto;
import com.example.team10searchengine.shared.ResponseDto;
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
public class KorDictService {

    private final KorDictMapper korDictMapper;

    private final KorDictMongoRepository korDictMongoRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public ResponseEntity<?> searchKorDictNgramSort(String keyword) {
        String noBlankKeyword = keyword.replace(" ", "");
        updateScoreForRanking(noBlankKeyword);

        // 한글자 검색시 Btree인덱스 사용
        if(keyword.length() == 1) {
            List<KorDict> kordict = korDictMapper.findByKeywordLike(keyword);
            return new ResponseEntity<>(ResponseDto.success(kordict), HttpStatus.OK);
        }

        // 몽고DB 확인
        KorDictMongo korDictMongoList = korDictMongoRepository.findByKeyword(noBlankKeyword);

        // MongoDB에 해당 키워드가 없으면 Mysql사용 후 MongoDB에 저장
        if(korDictMongoList == null) {
            List<KorDict> korDictResponseDto = korDictMapper.findByKeywordNgramV2(keyword);
            List<KorDictSortResDto> korDictResponseDtoList = getSortedKorDictList(korDictResponseDto, keyword);
            KorDictMongo korDictMongo = new KorDictMongo(noBlankKeyword,korDictResponseDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
            korDictMongoRepository.save(korDictMongo);
            return new ResponseEntity<>(ResponseDto.success(korDictResponseDtoList), HttpStatus.OK);
        }

        return new ResponseEntity<>(ResponseDto.success(korDictMongoList.getData()),HttpStatus.OK);
    }

    // ngram으로 정렬되어진 데이터를 원하는 방향으로 재정렬 (Scoring)
    public List<KorDictSortResDto> getSortedKorDictList(List<KorDict> korDictList, String keyword) {
        List<KorDictSortResDto> korDictResponseDtoList = new ArrayList<>();

        String[] keywordArr = keyword.split(" ");
        String noBlankKeyword = keyword.replace(" ", "");
        for (KorDict korDictResponseDto : korDictList) {
            String noSlashKeyword = korDictResponseDto.getWord().replace("-", "");

            int gain = keywordArr.length;
            int score = 0;

            if(noBlankKeyword.equals(noSlashKeyword)){
                score += gain +1;
            }

            for (String word : keywordArr) {
                if (noSlashKeyword.equals(word)) {
                    score += gain;
                }
                gain -= 1;
            }
            KorDictSortResDto korDictResDto = KorDictSortResDto.builder()
                    .id(korDictResponseDto.getId())
                    .word(korDictResponseDto.getWord())
                    .meaning(korDictResponseDto.getMeaning())
                    .pronunciation(korDictResponseDto.getPronunciation())
                    .example(korDictResponseDto.getExample())
                    .classification(korDictResponseDto.getClassification())
                    .score(score)
                    .build();
            korDictResponseDtoList.add(korDictResDto);
        }
        korDictResponseDtoList.sort(new korListComparator());

        return korDictResponseDtoList;
    }


    // Redis 사용하여 인기검색어 구현
    @Transactional
    public List<RankResponseDto> getKorDictRankList(){
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = ZSetOperations.reverseRangeWithScores("korranking", 0, 9);
        return typedTuples.stream().map(RankResponseDto::convertToRankResponseDto).collect(Collectors.toList());
    }

    public void updateScoreForRanking(String keyword){
        redisTemplate.opsForZSet().incrementScore("korranking", keyword, 1);
    }
}
