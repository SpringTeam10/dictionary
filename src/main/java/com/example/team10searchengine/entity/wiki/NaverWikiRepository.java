package com.example.team10searchengine.entity.wiki;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NaverWikiRepository extends JpaRepository<NaverWiki, Long> {
    @Query("select nw from naver_wikipedia nw where nw.keyword like %:keyword%") //QueryDSL
    List<NaverWiki> findByKeyword(@Param("keyword")String keyword);
}
