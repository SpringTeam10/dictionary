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
    public void deleteMongo() {
        log.info("백과사전 몽고DB 삭제 스케줄러 실행");
        List<KorDictMongo> korDictMongoList = korDictMongoRepository.findAll();

        int length = korDictMongoList.size();

        if(length <= 200) {
            log.info("삭제할 데이터가 없습니다.");
            return;
        }

        int curIndex = length - 201;
        while(curIndex >= 0){
            korDictMongoRepository.deleteById(korDictMongoList.get(curIndex).get_id());
            curIndex -= 1;

        }

        log.info("몽고스케줄러 종료");
    }
}
