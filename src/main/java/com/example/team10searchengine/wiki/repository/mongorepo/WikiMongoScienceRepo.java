package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoScience;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WikiMongoScienceRepo extends MongoRepository<WikiMongoScience,String> {
    WikiMongoScience findByKeyword(String keyword);
}
