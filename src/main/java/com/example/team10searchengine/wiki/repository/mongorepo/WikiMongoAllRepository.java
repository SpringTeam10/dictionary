package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoAll;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WikiMongoAllRepository extends MongoRepository<WikiMongoAll,String> {
    WikiMongoAll findByKeyword(String keyword);
    List<WikiMongoAll> findAllByOrderByCreatedAtDesc();
    List<WikiMongoAll> findAll();
    Long countBy();
}
