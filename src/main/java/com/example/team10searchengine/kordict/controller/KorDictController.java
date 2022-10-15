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
    // no offset 사용
//    @GetMapping("/search/kordict")
//    public ResponseEntity<?> findByNgramParserNoOffset(@RequestParam String keyword,
//                                                       @RequestParam(value="korDictId",required = false)Long korDictId,
//                                                       @RequestParam(value="checkId",required = false) Long[] checkId) {
//
//        return korDictService.findByNgramParserNoOffset(keyword,korDictId,checkId);
//    }

    // service 단에서 sort
    @GetMapping("/search/kordict")
    public ResponseEntity<?> findByNgramParser(@RequestParam String keyword) {

        return korDictService.searchKorDictNgramSort(keyword);
    }

}
