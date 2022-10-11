package com.example.team10searchengine;

import com.example.team10searchengine.entity.kordict.KorDict;
import com.example.team10searchengine.entity.weke.Weke;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final service service;

    @GetMapping("/kors")
    public Page<KorDict> getAll(@RequestParam("page")int page,
                                @RequestParam("size")int size) {return service.getAll(page,size);}
    @GetMapping("/kor")
    public List<KorDict> getKeyword(@RequestParam String keyword) {
        return service.getKeyword(keyword);}

    @GetMapping("/searchByKeyword/{keyword}")
    public List<Weke> searchByKeyword(@PathVariable String keyword){
        return service.searchByKeyword(keyword);
    }

    @GetMapping("/dict")
    public List<KorDict> searchDictPropose(@RequestParam String keyword) { return service.searchDictPropose(keyword);}

    @GetMapping("/mybatis")
    public List<KorDict> findByKeyword() {
        return service.findByKeyword();
    }
    @GetMapping("/mybatis/ngram")
    public List<KorDict> findByNgramParser(@RequestParam String keyword) {
        return service.findByNgramParser(keyword);
    }
    @GetMapping("/mybatis/ngram/nooffset")
    public List<KorDict> findByNgramParserNoOffset(@RequestParam String keyword,
                                                   @RequestParam(value="korDictId",required = false)Long korDictId,
                                                   @RequestParam(value="checkId",required = false) Long[] checkId) {
        return service.findByNgramParserNoOffset(keyword,korDictId,checkId);
    }

    @GetMapping("/nooffset")
    public List<KorDict> getKeywordNoOffset(@RequestParam String keyword,
                                             @RequestParam(value="korDictId",required = false)Long korDictId,
                                             @PageableDefault() Pageable pageable){
        return service.getKeywordNoOffset(keyword,korDictId,pageable);
    }
}
