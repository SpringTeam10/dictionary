package com.example.team10searchengine;

import com.example.team10searchengine.entity.kordict.KorDict;
import com.example.team10searchengine.entity.weke.Weke;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final service service;

    @GetMapping("/search")
    public List<Weke> search(@RequestParam String keyword){
        return service.search(keyword);
    }

    @GetMapping("/kors")
    public Page<KorDict> getAll(@RequestParam("page")int page,
                                @RequestParam("size")int size) {return service.getAll(page,size);}
    @GetMapping("/kor")
    public List<KorDict> getKeyword(@RequestParam String keyword) {return service.getKeyword(keyword);}
}
