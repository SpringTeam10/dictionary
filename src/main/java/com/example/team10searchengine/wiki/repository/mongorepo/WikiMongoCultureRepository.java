package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoCulture;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WikiMongoCultureRepository extends MongoRepository<WikiMongoCulture,String> {
    WikiMongoCulture findByKeyword(String keyword);
    List<WikiMongoCulture> findAllByOrderByCreatedAtDesc();
    Long countBy();
}
