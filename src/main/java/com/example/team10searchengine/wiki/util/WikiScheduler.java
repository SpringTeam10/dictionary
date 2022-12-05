package com.example.team10searchengine.wiki.util;

import com.example.team10searchengine.wiki.entity.WikiMongoTotal;
import com.example.team10searchengine.wiki.repository.mongorepo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class WikiScheduler {

    private final WikiMongoTotalRepo wikiMongoTotalRepo;
    private final WikiMongoCultureRepo wikiMongoCultureRepo;
    private final WikiMongoEtcRepo wikiMongoEtcRepo;
    private final WikiMongoHistoryRepo wikiMongoHistoryRepo;
    private final WikiMongoScienceRepo wikiMongoScienceRepo;
    private final WikiMongoSocialRepo wikiMongoSocialRepo;

//    @Scheduled(cron = "0 0 4 * * *")
//    public void deleteWikiAllCat() {
//        log.info("위키 전체 검색 몽고 디비 삭제 스케쥴러");
//        List<WikiMongoAll> wikiMongoAllList = wikiMongoAllRepository.findAllByOrderByCreatedAtDesc();
//
//        int curIndex = wikiMongoAllList.size();
//
//        if(curIndex <= 200) {
//            log.info("삭제할 데이터가 없습니다.");
//            return;
//        }
//
//        while(curIndex > 201){
//            curIndex -= 1;
//            wikiMongoAllRepository.deleteById(wikiMongoAllList.get(curIndex).get_id());
//        }
//
//        log.info("몽고스케줄러 종료");
//    }

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteWikiAllCat() {
        log.info("위키 전체 검색 몽고 디비 삭제 스케쥴러");
        List<WikiMongoTotal> wikiMongoTotalList = wikiMongoTotalRepo.findAll();

        int length = wikiMongoTotalList.size();

        if(length <= 200) {
            log.info("삭제할 데이터가 없습니다.");
            return;
        }

        int curIndex = length - 201;
        while(curIndex >= 0){
            wikiMongoTotalRepo.deleteById(wikiMongoTotalList.get(curIndex).get_id());
            curIndex -= 1;
        }

        log.info("몽고스케줄러 종료");
    }
}
