package com.example.team10searchengine;

import com.example.team10searchengine.kordict.KorDict;
import com.example.team10searchengine.kordict.KorDictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class service {
    private final KorDictRepository korDictRepository;


//    public List<NaverWiki> search(String keyword) {
//        if (keyword.equals("JJang")) {
////            Weke entity = new Weke("신짱구","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRILAevTjTaM2KwU1ojU3UPvesg6ANFoI9J-A&usqp=CAU", "짱구!", "https://www.naver.com", "JJang");
//            List<NaverWiki> entityList = new ArrayList<>();
////            entityList.add(entity);
//            return entityList;
//        }
//        return null;
//    }

    @Transactional
    public Page<KorDict> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return korDictRepository.findAll(pageable);
    }
    @Transactional
    public List<KorDict> getKeyword(String keyword) {
        return korDictRepository.findByWordOrMeaning(keyword);
    }

}
