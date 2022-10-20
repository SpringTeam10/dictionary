package com.example.team10searchengine.wiki.util;

import com.example.team10searchengine.wiki.entity.WikiMongoAll;
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

    private final WikiMongoAllRepository wikiMongoAllRepository;
    private final WikiMongoCultureRepository wikiMongoCultureRepository;
    private final WikiMongoEtcRepository wikiMongoEtcRepository;
    private final WikiMongoHistoryRepository wikiMongoHistoryRepository;
    private final WikiMongoScienceRepository wikiMongoScienceRepository;
    private final WikiMongoSocialRepository wikiMongoSocialRepository;

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
        List<WikiMongoAll> wikiMongoAllList = wikiMongoAllRepository.findAll();

        int length = wikiMongoAllList.size();

        if(length <= 200) {
            log.info("삭제할 데이터가 없습니다.");
            return;
        }

        int curIndex = length - 201;
        while(curIndex >= 0){
            wikiMongoAllRepository.deleteById(wikiMongoAllList.get(curIndex).get_id());
            curIndex -= 1;
        }

        log.info("몽고스케줄러 종료");
    }
}
