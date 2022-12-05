package com.example.team10searchengine.wiki.repository.mongorepo;

import com.example.team10searchengine.wiki.entity.WikiMongoSocial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WikiMongoSocialRepo extends MongoRepository<WikiMongoSocial,String> {
    WikiMongoSocial findByKeyword(String keyword);
    List<WikiMongoSocial> findAllByOrderByCreatedAtDesc();
    Long countBy();
}
