package com.example.team10searchengine.wiki.jpqlrepo;

import com.example.team10searchengine.wiki.entity.Wiki;
import com.example.team10searchengine.wiki.dto.WikiResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiFullNaturalRepository extends JpaRepository<Wiki, Long> {
    @Query(value = "SELECT w.id, w.keyword, w.contents, w.img_url, w.detail_url, w.classification " +
            "FROM wiki w WHERE MATCH (w.keyword) AGAINST (:keyword) limit 500",nativeQuery = true)
    List<WikiResDto> findByKeyword(@Param("keyword")String keyword);
}
