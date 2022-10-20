package com.example.team10searchengine.kordict.repository.mongorepo;

import com.example.team10searchengine.kordict.entity.KorDictMongo;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface KorDictMongoRepository extends MongoRepository<KorDictMongo,String> {
    KorDictMongo findByKeyword(String keyword);

}
