package com.example.team10searchengine.entity.kordict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KorDictRepository extends JpaRepository<KorDict,Long>, KorDictCustomRepository {
    Page<KorDict> findAll(Pageable pageable);

    /* like 사용 */
//    @Query("select d from woori_fountain d where d.word like %:word%")
    @Query(value = "SELECT * FROM woori_fountain WHERE MATCH (word) AGAINST (:word in boolean mode) order by word=:word desc, word asc",nativeQuery = true)
    List<KorDict> findByWord(@Param("word")String word);
}
