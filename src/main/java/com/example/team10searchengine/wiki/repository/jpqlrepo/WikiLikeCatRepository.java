package com.example.team10searchengine.wiki.repository.jpqlrepo;

import com.example.team10searchengine.wiki.entity.Wiki;
import com.example.team10searchengine.wiki.dto.WikiJpqlResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WikiLikeCatRepository extends JpaRepository<Wiki, Long> {
    @Query(value = "select w.id, w.keyword, w.contents, w.img_url, w.detail_url, w.classification " +
            "from wiki w where w.keyword like :keyword and w.category = :category limit 500", nativeQuery = true)
    List<WikiJpqlResDto> findByKeyword(@Param("keyword")String keyword, @Param("category")String category);
}
