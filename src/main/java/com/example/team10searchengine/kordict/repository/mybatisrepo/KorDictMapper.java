package com.example.team10searchengine.kordict.repository.mybatisrepo;

import com.example.team10searchengine.kordict.entity.KorDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KorDictMapper {
    List<KorDict> findByKeywordLike(String keyword);
    List<KorDict> findByKeywordNgram(String keyword,Long korDictId);
}
