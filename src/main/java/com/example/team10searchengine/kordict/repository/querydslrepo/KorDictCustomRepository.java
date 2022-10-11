package com.example.team10searchengine.kordict.repository.querydslrepo;

import com.example.team10searchengine.kordict.entity.KorDict;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KorDictCustomRepository {
    List<KorDict> findByWordUsingQuerydsl(String word, Long korDictId, Pageable pageable);
}
