package com.example.team10searchengine.entity.kordict;

import java.util.List;

public interface KorDictCustomRepository {
    List<KorDict> findByWordUsingQuerydsl(String word);
}
