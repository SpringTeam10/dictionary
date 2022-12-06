package com.example.team10searchengine.wiki.repository.mybatisrepo;

import com.example.team10searchengine.wiki.dto.WikiResDto;
import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WikiMapper {
    List<WikiSortResDto> findByKeywordLike(String keyword);
    List<WikiSortResDto> findByKeywordAndCategoryLike(String keyword, String category);
    List<WikiResDto> findByKeywordOne(String keyword);
    List<WikiResDto> findByKeywordAndCategoryOne(String keyword, String category);
    List<WikiResDto> findByKeywordNgram(String keyword);
    List<WikiResDto> findByKeywordAndCategoryNgram(String keyword, String category);
}
