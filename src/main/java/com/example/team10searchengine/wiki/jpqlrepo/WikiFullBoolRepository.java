package com.example.team10searchengine.wiki.jpqlrepo;

import com.example.team10searchengine.wiki.entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiFullBoolRepository extends JpaRepository<Wiki, Long> {
    @Query(value = "SELECT * FROM wiki w WHERE MATCH (w.keyword) AGAINST (:keyword in boolean mode) limit 500",nativeQuery = true) // jpql
    List<Wiki> findByKeyword(@Param("keyword")String keyword);
}
