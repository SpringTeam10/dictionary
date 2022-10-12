package com.example.team10searchengine.kordict.service;

import com.example.team10searchengine.kordict.dto.KorDictResponseDto;
import com.example.team10searchengine.kordict.entity.KorDict;
import com.example.team10searchengine.kordict.repository.mybatisrepo.KorDictMapper;
import com.example.team10searchengine.shared.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
                    .is_unique(korDict.getIsUnique())
                    .pronunciation(korDict.getPronunciation())
                    .part(korDict.getPart())
                    .meaning(korDict.getMeaning())
                    .example(korDict.getExample())
                    .field(korDict.getField())
                    .proverb(korDict.getProverb())
                    .idiom(korDict.getIdiom())
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

    @Scheduled(cron = "0 0 4 * * * ", zone = "Asia/Seoul")
    public void refreshDeleteId() {
        log.info("deleteId scheduler 작동");
        deleteId = new ArrayList<>();
    }

}
