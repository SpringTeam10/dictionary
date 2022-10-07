package com.example.team10searchengine.entity.kordict.querydslrepo;

import com.example.team10searchengine.entity.kordict.KorDict;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KorDictCustomRepository {
    List<KorDict> findByWordUsingQuerydsl(String word, Long korDictId, Pageable pageable);
}
