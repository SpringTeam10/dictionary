package com.example.team10searchengine.kordict.repository.mongorepo;

import com.example.team10searchengine.kordict.entity.KorDictMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface KorDictMongoRepository extends MongoRepository<KorDictMongo,String> {
    List<KorDictMongo> findByKeyword(String keyword);
    List<KorDictMongo> findAllByOrderByCreatedAtAsc();
    Long countBy();
}
