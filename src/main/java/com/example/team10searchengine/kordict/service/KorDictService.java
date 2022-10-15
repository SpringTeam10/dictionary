package com.example.team10searchengine.kordict.service;

import com.example.team10searchengine.kordict.dto.KorDictResponseDto;
import com.example.team10searchengine.kordict.entity.KorDict;
import com.example.team10searchengine.kordict.repository.mybatisrepo.KorDictMapper;
import com.example.team10searchengine.kordict.util.korListComparator;
import com.example.team10searchengine.shared.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class KorDictService {

    private final KorDictMapper korDictMapper;

    private List<Long> deleteId = new ArrayList<>();


    // Nooffset을 이용한 페이징 검색 (Mybatis) / 한글자 검색 = B-Tree Like
//    public ResponseEntity<?> findByNgramParserNoOffset(String keyword,Long korDictId,Long[] checkId)
    public ResponseEntity<?> findByNgramParserNoOffset(String keyword,Long korDictId,Long[] checkId)
    {
        if(keyword.length() == 1) {
            List<KorDict> kordict = korDictMapper.findByKeywordLike(keyword);
            return new ResponseEntity<>(ResponseDto.success(kordict), HttpStatus.OK);
        }
        List<KorDict> KorDictList = korDictMapper.findByKeywordNgram(keyword,korDictId);
        List<KorDictResponseDto> korDicts = new ArrayList<>();

        for(KorDict korDict : KorDictList) {
            korDicts.add(KorDictResponseDto.builder()
                    .id(korDict.getId())
                    .word(korDict.getWord())
                    .pronunciation(korDict.getPronunciation())
                    .meaning(korDict.getMeaning())
                    .example(korDict.getExample())
                    .classification(korDict.getClassification())
                    .build());
        }

        if(korDictId != null){
            for(int i=0; i<korDicts.size();i++) {
                for(Long delete : deleteId){
                    if(delete.equals(korDicts.get(i).getId())) {
                        korDicts.remove(i);
                    }
                }
                for(Long check : checkId){
                    if(check.equals(korDicts.get(i).getId())){
                        korDicts.remove(i);
                        deleteId.add(check);
                    }
                }
            }
        }

        return new ResponseEntity<>(ResponseDto.success(korDicts), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> searchKorDictNgramSort(String keyword) {
        long init = System.currentTimeMillis();

        if(keyword.length() == 1) {
            List<KorDict> kordict = korDictMapper.findByKeywordLike(keyword);
            return new ResponseEntity<>(ResponseDto.success(kordict), HttpStatus.OK);
        }

        List<KorDict> korDictResponseDto = korDictMapper.findByKeywordNgramV2(keyword);

        List<KorDictResponseDto> korDictResponseDtoList = getSortedKorDictList(korDictResponseDto, keyword);

        return new ResponseEntity<>(ResponseDto.success(korDictResponseDtoList), HttpStatus.OK);
    }

    public List<KorDictResponseDto> getSortedKorDictList(List<KorDict> korDictList, String keyword) {
        List<KorDictResponseDto> korDictResponseDtoList = new ArrayList<>();

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
            KorDictResponseDto korDictResDto = KorDictResponseDto.builder()
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

    @Scheduled(cron = "0 0 4 * * * ", zone = "Asia/Seoul")
    public void refreshDeleteId() {
        log.info("deleteId scheduler 작동");
        deleteId = new ArrayList<>();
    }

}
