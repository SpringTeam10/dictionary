package com.example.team10searchengine.kordict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KorDictRepository extends JpaRepository<KorDict,Long> {
    Page<KorDict> findAll(Pageable pageable);
    @Query("select d from dictionary d where d.word like %:word%")
    List<KorDict> findByWordOrMeaning(@Param("word")String word);
}
