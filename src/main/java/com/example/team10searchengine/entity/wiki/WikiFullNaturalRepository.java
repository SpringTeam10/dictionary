package com.example.team10searchengine.entity.wiki;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiFullNaturalRepository extends JpaRepository<Wiki, Long> {
    @Query(value = "SELECT * FROM wiki WHERE MATCH (keyword) AGAINST (:keyword) limit 500",nativeQuery = true) // jpql
    List<Wiki> findByKeyword(@Param("keyword")String keyword);
}
