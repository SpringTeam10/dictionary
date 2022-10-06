package com.example.team10searchengine.entity.wiki;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiLikeRepository extends JpaRepository<Wiki, Long> {
    @Query("select nw from wiki nw where nw.keyword like :keyword%") // jpql
    List<Wiki> findByKeyword(@Param("keyword")String keyword);
}
