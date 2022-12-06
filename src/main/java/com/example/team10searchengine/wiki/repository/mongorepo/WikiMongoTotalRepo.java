package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoTotal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WikiMongoTotalRepo extends MongoRepository<WikiMongoTotal,String> {
    WikiMongoTotal findByKeyword(String keyword);
    List<WikiMongoTotal> findAll();
}
