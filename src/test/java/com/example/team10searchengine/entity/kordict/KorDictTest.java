package com.example.team10searchengine.entity.kordict;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KorDictTest {
    @Test
<<<<<<< Updated upstream
    @DisplayName("Querydsl 가능?")
    void jpa_findWord() {
=======
    @DisplayName("RedisTemplate")
    void redisTemplate() {
        final String key = "상추";

        final ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set(key,"1"); // redis set
        final String result_1 = valueOperations.get(key); // redis get

        System.out.println("result_1 = " + result_1);

        valueOperations.increment(key); // redis incr
        final String result_2 = valueOperations.get(key);

        System.out.println("result_2 = " + result_2);
>>>>>>> Stashed changes
    }
}