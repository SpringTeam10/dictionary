package com.example.team10searchengine.wiki.jpqlrepo;

import com.example.team10searchengine.wiki.entity.Wiki;
import com.example.team10searchengine.wiki.dto.WikiJpqlResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiFullNaturalCatRepository extends JpaRepository<Wiki, Long> {
    @Query(value = "SELECT w.id, w.keyword, w.contents, w.img_url, w.detail_url, w.classification " +
            "FROM wiki w WHERE MATCH (w.keyword) AGAINST (:keyword) AND w.category = (:category) limit 500",nativeQuery = true) // jpql
    List<WikiJpqlResDto> findByKeywordAndCategory(@Param("keyword")String keyword, @Param("category")String category);
}
