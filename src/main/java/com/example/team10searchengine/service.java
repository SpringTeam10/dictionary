package com.example.team10searchengine;

import com.example.team10searchengine.entity.kordict.KorDict;
import com.example.team10searchengine.entity.kordict.KorDictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class service {
    private final KorDictRepository korDictRepository;


    @Transactional
    public Page<KorDict> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return korDictRepository.findAll(pageable);
    }
    @Transactional
    public List<KorDict> getKeyword(String keyword) {
        long startTime = System.currentTimeMillis();

        List<KorDict> korDictList = korDictRepository.findByWordUsingQuerydsl(keyword);
        long endTime = System.currentTimeMillis();
        log.info("time : {}" , endTime - startTime);
        return korDictList;
    }

}
