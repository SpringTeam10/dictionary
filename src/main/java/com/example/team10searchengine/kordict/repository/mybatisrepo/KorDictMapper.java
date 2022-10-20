package com.example.team10searchengine.kordict.repository.mybatisrepo;

import com.example.team10searchengine.kordict.entity.KorDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KorDictMapper {
    // B-tree index 사용
    List<KorDict> findByKeywordLike(String keyword);
    
    // ngram 서비스단에서 정렬
    List<KorDict> findByKeywordNgramV2(String keyword);

}
