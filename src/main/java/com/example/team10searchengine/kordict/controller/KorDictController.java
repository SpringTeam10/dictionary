package com.example.team10searchengine.kordict.controller;

import com.example.team10searchengine.kordict.service.KorDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KorDictController {
    private final KorDictService korDictService;


    @GetMapping("/btree")
    public ResponseEntity<?> findByKeyword(@RequestParam String keyword) {
        return korDictService.findByKeyword(keyword);
    }

    @GetMapping("/mybatis/ngram/nooffset")
    public ResponseEntity<?> findByNgramParserNoOffset(@RequestParam String keyword,
                                                       @RequestParam(value="korDictId",required = false)Long korDictId,
                                                       @RequestParam(value="checkId",required = false) Long[] checkId) {
        return korDictService.findByNgramParserNoOffset(keyword,korDictId,checkId);
    }

}
