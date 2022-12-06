package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoEtc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WikiMongoEtcRepo extends MongoRepository<WikiMongoEtc,String> {
    WikiMongoEtc findByKeyword(String keyword);
}
