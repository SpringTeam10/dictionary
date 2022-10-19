package com.example.team10searchengine.kordict.service;

import com.example.team10searchengine.kordict.dto.KorDictSortResponseDto;
import com.example.team10searchengine.kordict.entity.KorDict;
import com.example.team10searchengine.kordict.entity.KorDictMongo;
import com.example.team10searchengine.kordict.repository.mongorepo.KorDictMongoRepository;
import com.example.team10searchengine.kordict.repository.mybatisrepo.KorDictMapper;
import com.example.team10searchengine.kordict.util.korListComparator;
import com.example.team10searchengine.shared.ResponseDto;
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
public class KorDictService {

    private final KorDictMapper korDictMapper;

    private final KorDictMongoRepository korDictMongoRepository;


    @Transactional
    public ResponseEntity<?> searchKorDictNgramSort(String keyword) {
        Long info = System.currentTimeMillis();

        if(keyword.length() == 1) {
            List<KorDict> kordict = korDictMapper.findByKeywordLike(keyword);
            return new ResponseEntity<>(ResponseDto.success(kordict), HttpStatus.OK);
        }
        String noBlankKeyword = keyword.replace(" ", "");
        if(korDictMongoRepository.findByKeyword(noBlankKeyword).size() != 0) {
            return mongo(keyword,info);
        }
        List<KorDict> korDictResponseDto = korDictMapper.findByKeywordNgramV2(keyword);

        List<KorDictSortResponseDto> korDictResponseDtoList = getSortedKorDictList(korDictResponseDto, keyword);
        log.info("local millis : {}",System.currentTimeMillis() - info);
        return new ResponseEntity<>(ResponseDto.success(korDictResponseDtoList), HttpStatus.OK);
    }

    public List<KorDictSortResponseDto> getSortedKorDictList(List<KorDict> korDictList, String keyword) {
        List<KorDictSortResponseDto> korDictResponseDtoList = new ArrayList<>();

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
            KorDictSortResponseDto korDictResDto = KorDictSortResponseDto.builder()
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

        KorDictMongo korDictMongo = new KorDictMongo(noBlankKeyword,korDictResponseDtoList, LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        korDictResponseDtoList.sort(new korListComparator());
        korDictMongoRepository.save(korDictMongo);

        return korDictResponseDtoList;
    }

    public ResponseEntity<?> mongo(String keyword,Long info) {

        List<KorDictMongo> korDictMongoList = korDictMongoRepository.findByKeyword(keyword);
        List<KorDictSortResponseDto> korDictSortResponseDtoList = new ArrayList<>();

        for(KorDictMongo korDictMongo : korDictMongoList) {
            for(int i=0 ; i<korDictMongo.getData().size(); i++) {
                KorDictSortResponseDto korDictSortResponseDto = KorDictSortResponseDto.builder()
                        .id(korDictMongo.getData().get(i).getId())
                        .word(korDictMongo.getData().get(i).getWord())
                        .meaning(korDictMongo.getData().get(i).getMeaning())
                        .pronunciation(korDictMongo.getData().get(i).getPronunciation())
                        .example(korDictMongo.getData().get(i).getExample())
                        .classification(korDictMongo.getData().get(i).getClassification())
                        .score(korDictMongo.getData().get(i).getScore())
                        .build();
                korDictSortResponseDtoList.add(korDictSortResponseDto);
            }
        }
        log.info("mongo millis : {}",System.currentTimeMillis() - info);
        return new ResponseEntity<>(ResponseDto.success(korDictSortResponseDtoList),HttpStatus.OK);
    }
}
