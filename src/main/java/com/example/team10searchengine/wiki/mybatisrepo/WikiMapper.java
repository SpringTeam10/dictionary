package com.example.team10searchengine.wiki.mybatisrepo;

import com.example.team10searchengine.wiki.dto.WikiResDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WikiMapper {
    List<WikiResDto> findByKeywordLike(String keyword);
    List<WikiResDto> findByKeywordAndCategoryLike(String keyword, String category);
    List<WikiResDto> findByKeywordNgram(String keyword);
    List<WikiResDto> findByKeywordAndCategoryNgram(String keyword, String category);
}
