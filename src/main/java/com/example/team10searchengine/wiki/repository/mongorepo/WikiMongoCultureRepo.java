package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoCulture;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WikiMongoCultureRepo extends MongoRepository<WikiMongoCulture,String> {
    WikiMongoCulture findByKeyword(String keyword);
}
