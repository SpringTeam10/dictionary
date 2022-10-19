package com.example.team10searchengine.entity.kordict;

import com.example.team10searchengine.kordict.entity.KorDictMongo;
import com.example.team10searchengine.kordict.repository.mongorepo.KorDictMongoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class KorDictMongoTest {

    @Autowired
    private KorDictMongoRepository korDictMongoRepository;

    @Test
    @DisplayName("MongoRepository test")
    void mongoTest() {
        List<KorDictMongo> korDictMongoList = korDictMongoRepository.findByKeyword("가다");
        List<KorDictMongo> korDictMongos = new ArrayList<>();

        for(KorDictMongo korDictMongo : korDictMongoList) {
            korDictMongos.add((KorDictMongo) korDictMongo.getData());

        }
        System.out.println("keyword : " + korDictMongos);
    }

}

