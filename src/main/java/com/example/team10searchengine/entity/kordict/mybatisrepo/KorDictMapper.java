package com.example.team10searchengine.entity.kordict.mybatisrepo;

import com.example.team10searchengine.entity.kordict.KorDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KorDictMapper {
    List<KorDict> findByKeyword();
    List<KorDict> findByNgramParser(String keyword);
    List<KorDict> findByNgramParserNoOffset(String keyword,Long korDictId);
}
