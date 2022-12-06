package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoSocial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WikiMongoSocialRepo extends MongoRepository<WikiMongoSocial,String> {
    WikiMongoSocial findByKeyword(String keyword);
}
