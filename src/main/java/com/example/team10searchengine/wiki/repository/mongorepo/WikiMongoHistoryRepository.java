package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WikiMongoHistoryRepository extends MongoRepository<WikiMongoHistory,String> {
    WikiMongoHistory findByKeyword(String keyword);
    List<WikiMongoHistory> findAllByOrderByCreatedAtDesc();
    Long countBy();
}
