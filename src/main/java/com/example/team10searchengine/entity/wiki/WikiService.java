package com.example.team10searchengine.entity.wiki;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WikiService {
    private final WikiSameRepository wikiSameRepository;
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiFullNaturalRepository wikiFullNaturalRepository;
    private final WikiFullBoolRepository wikiFullBoolRepository;


    @Transactional
    public List<Wiki>searchWiki(String keyword) {
        long init = System.currentTimeMillis();
        List<Wiki> wikiList = wikiFullNaturalRepository.findByKeyword(keyword);
        log.info(String.valueOf(System.currentTimeMillis() - init));
        return wikiList;
    }

    @Transactional
    public List<Wiki>searchWikiNgramFiltering(String keyword) {
        long init = System.currentTimeMillis();
        List<Wiki> wikiList = wikiFullNaturalRepository.findByKeyword(keyword);
        List<Wiki> wikiFilteredList = new ArrayList<>();

        for(Wiki wiki : wikiList){
            String noBlankKeyword = wiki.getKeyword().replace(" ","").toLowerCase();
            String noBlankSearchKeyword = keyword.replace(" ","").toLowerCase();
            if(noBlankKeyword.contains(noBlankSearchKeyword)){
                wikiFilteredList.add(wiki);
            }
        }

        log.info(String.valueOf(System.currentTimeMillis() - init));
        return wikiFilteredList;
    }

    @Transactional
    public List<WikiSortDto>searchWikiNgramSortFilter(String searchWord) {
        long init = System.currentTimeMillis();
        List<Wiki> wikiList = wikiFullNaturalRepository.findByKeyword(searchWord);
        List<WikiSortDto> wikiSortDtoList = new ArrayList<>();

        String[] searchWordArr = searchWord.split(" ");

        for(Wiki wiki : wikiList){
            String noBlankKeyword = wiki.getKeyword().replace(" ","").toLowerCase();

            int num = 0;

            for(String word: searchWordArr){
                if(noBlankKeyword.contains(word.toLowerCase())){
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
                        .category(wiki.getCategory())
                        .num(num)
                        .build();

                wikiSortDtoList.add(wikiSortDto);
            }

        }

        wikiSortDtoList.sort(new ListComparator());

        log.info(String.valueOf(System.currentTimeMillis() - init));
        return wikiSortDtoList;
    }


    @Transactional
    public List<Wiki>searchWikiLikeToken(String keyword) {
        long init = System.currentTimeMillis();
        String init_keyword = keyword;
        // like repository에서 keyword끝에 % 붙임
        if(keyword.contains(" ")){
            keyword = keyword.replace(" ","%");
        }else{
            StringBuilder buf = new StringBuilder(keyword);
            int length = keyword.length();
            if(length > 2){
                int offset = 2;
                int curlength = 2;
                while(curlength < length){
                    buf.insert(offset,"%");
                    offset += 3;
                    curlength += 2;
                }
                keyword = buf.toString();

            }
        }
        List<Wiki> wikiList = wikiLikeRepository.findByKeyword(keyword);

        if(wikiList.isEmpty()){
            List<Wiki> wikiListFull = wikiFullNaturalRepository.findByKeyword(init_keyword);

            log.info(String.valueOf(System.currentTimeMillis() - init));
            log.info(keyword);
            return wikiListFull;
        }
        log.info(String.valueOf(System.currentTimeMillis() - init));
        log.info(keyword);
        return wikiList;
    }
}
