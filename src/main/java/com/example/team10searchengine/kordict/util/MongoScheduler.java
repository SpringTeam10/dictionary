package com.example.team10searchengine.kordict.util;

import com.example.team10searchengine.kordict.entity.KorDictMongo;
import com.example.team10searchengine.kordict.repository.mongorepo.KorDictMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongoScheduler {
    private final KorDictMongoRepository korDictMongoRepository;

    @Scheduled(cron = "0 0 4 * * *")
    public void deletePost() {
        log.info("몽고 스케줄러 실행");
        List<KorDictMongo> korDictMongoList = korDictMongoRepository.findAllByOrderByCreatedAtAsc();

        if(korDictMongoList.size() <= 200) {
            log.info("삭제할 데이터가 없습니다.");
        }
        else {
            for(int i = 0; i<korDictMongoList.size();i++){
                Long mongoSizeCheck = korDictMongoRepository.countBy();
                KorDictMongo korDictMongo = korDictMongoList.get(i);
                if(mongoSizeCheck == 200)
                    break;
                else {
                    korDictMongoRepository.deleteById(korDictMongo.get_id());
                    log.info("keyword : {} 삭제",korDictMongo.getKeyword());
                }
            }
        }
        log.info("몽고스케줄러 종료");
    }
}
