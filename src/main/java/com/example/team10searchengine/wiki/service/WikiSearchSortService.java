package com.example.team10searchengine.wiki.service;

import com.example.team10searchengine.wiki.ListComparator;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortDto;
import com.example.team10searchengine.wiki.entity.Wiki;
import com.example.team10searchengine.wiki.jpqlrepo.WikiLikeCatRepository;
import com.example.team10searchengine.shared.ResponseDto;
import com.example.team10searchengine.wiki.jpqlrepo.WikiFullNaturalCatRepository;
import com.example.team10searchengine.wiki.jpqlrepo.WikiFullNaturalRepository;
import com.example.team10searchengine.wiki.jpqlrepo.WikiLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikiSearchSortService {
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiLikeCatRepository wikiLikeCatRepository;
    private final WikiFullNaturalRepository wikiFullNaturalRepository;
    private final WikiFullNaturalCatRepository wikiFullNaturalCatRepository;


//    @Transactional
//    public List<Wiki>searchWikiNgramFiltering(String keyword) {
//        long init = System.currentTimeMillis();
//        List<Wiki> wikiList = wikiFullNaturalRepository.findByKeyword(keyword);
//        List<Wiki> wikiFilteredList = new ArrayList<>();
//
//        for(Wiki wiki : wikiList){
//            String noBlankKeyword = wiki.getKeyword().replace(" ","").toLowerCase();
//            String noBlankSearchKeyword = keyword.replace(" ","").toLowerCase();
//            if(noBlankKeyword.contains(noBlankSearchKeyword)){
//                wikiFilteredList.add(wiki);
//            }
//        }
//
//        log.info(String.valueOf(System.currentTimeMillis() - init));
//        return wikiFilteredList;
//    }
//
//    @Transactional
//    public List<Wiki>searchWikiLikeToken(String keyword) {
//        long init = System.currentTimeMillis();
//        String init_keyword = keyword;
//        // like repository에서 keyword끝에 % 붙임
//        if(keyword.contains(" ")){
//            keyword = keyword.replace(" ","%");
//        }else{
//            StringBuilder buf = new StringBuilder(keyword);
//            int length = keyword.length();
//            if(length > 2){
//                int offset = 2;
//                int curlength = 2;
//                while(curlength < length){
//                    buf.insert(offset,"%");
//                    offset += 3;
//                    curlength += 2;
//                }
//                keyword = buf.toString();
//
//            }
//        }
//        List<Wiki> wikiList = wikiLikeRepository.findByKeyword(keyword);
//
//        if(wikiList.isEmpty()){
//            List<Wiki> wikiListFull = wikiFullNaturalRepository.findByKeyword(init_keyword);
//
//            log.info(String.valueOf(System.currentTimeMillis() - init));
//            log.info(keyword);
//            return wikiListFull;
//        }
//        log.info(String.valueOf(System.currentTimeMillis() - init));
//        log.info(keyword);
//        return wikiList;
//    }


    @Transactional
    public ResponseEntity<?> searchWikiNgramSort(String keyword) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikiList = wikiFullNaturalRepository.findByKeyword(keyword);

        List<WikiSortDto> wikiSortDtoList = getSortedWikiList(wikiList, keyword);

        log.info("ms={}",System.currentTimeMillis() - init);
        log.info("sorted list length={}", wikiSortDtoList.size());
        log.info("original list length={}",wikiList.size());
        return new ResponseEntity<>(ResponseDto.success(wikiSortDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> searchWikiCatNgramSort(String keyword, String category) {
        long init = System.currentTimeMillis();

        List<WikiResDto> wikiList = wikiFullNaturalCatRepository.findByKeywordAndCategory(keyword, category);

        List<WikiSortDto> wikiSortDtoList = getSortedWikiList(wikiList, keyword);

        log.info("ms={}",System.currentTimeMillis() - init);
        log.info("sorted list length={}", wikiSortDtoList.size());
        log.info("original list length={}",wikiList.size());
        return new ResponseEntity<>(ResponseDto.success(wikiSortDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> searchWikiLikeToken(String keyword) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }
        List<WikiResDto> wikiList = wikiLikeRepository.findByKeyword(keyword);

        log.info(String.valueOf(System.currentTimeMillis() - init));
        log.info(keyword);

        return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> searchWikiCatLikeToken(String keyword, String category) {
        long init = System.currentTimeMillis();

        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }
        List<WikiResDto> wikiList = wikiLikeCatRepository.findByKeyword(keyword, category);

        log.info(String.valueOf(System.currentTimeMillis() - init));
        log.info(keyword);

        return new ResponseEntity<>(ResponseDto.success(wikiList), HttpStatus.OK);
    }

    public List<WikiSortDto> getSortedWikiList(List<WikiResDto> wikiList, String keyword){
        List<WikiSortDto> wikiSortDtoList = new ArrayList<>();

        String[] keywordArr = keyword.split(" ");

        for(WikiResDto wiki : wikiList){
            String noBlankKeyword = wiki.getKeyword().replace(" ","");

            int num = 0;

            for(String word: keywordArr){
                if(noBlankKeyword.contains(word)){
                    num += 1;
                }
            }

            if(num > 0){
                WikiSortDto wikiSortDto = WikiSortDto.builder()
                        .id(wiki.getId())
                        .keyword(wiki.getKeyword())
                        .contents(wiki.getContents())
                        .img_url(wiki.getImg_url())
                        .detail_url(wiki.getDetail_url())
                        .classification(wiki.getClassification())
                        .num(num)
                        .build();

                wikiSortDtoList.add(wikiSortDto);
            }

        }

        wikiSortDtoList.sort(new ListComparator());

        return wikiSortDtoList;
    }

}
