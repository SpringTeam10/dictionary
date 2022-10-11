package com.example.team10searchengine.entity.jpqlrepo;

import com.example.team10searchengine.entity.kordict.KorDict;
import com.example.team10searchengine.entity.kordict.querydslrepo.KorDictCustomRepository;
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
    @Query(value = "SELECT * FROM kor_dict " +
            "WHERE MATCH (word) AGAINST (:word in boolean mode)" +
            "order by word=:word desc, word asc",nativeQuery = true)
    List<KorDict> findByWord(@Param("word")String word);

    @Query("SELECT w FROM kor_dict w WHERE w.word LIKE :word")
    List<KorDict> findByBtreeWord(@Param("word")String word);
}
