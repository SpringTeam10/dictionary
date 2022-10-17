package com.example.team10searchengine.entity.kordict;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SpringBootTest
class KorDictTest {

    @Autowired
    private KorDictRedisRepository korDictRedisRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    @DisplayName("RedisRepository test")
    void redisTest() {
        KorDictLog korDictLog = new KorDictLog("한글");
        KorDictLog korDictLog2 = new KorDictLog("Korea");


        korDictRedisRepository.save(korDictLog);
        korDictRedisRepository.save(korDictLog2);
        System.out.println("save 완료");
        korDictRedisRepository.count();
        System.out.println("count : " + korDictRedisRepository.count());
    }

    @Test
    @DisplayName("RedisTemplate")
    void redisTemplate() {
        final String key = "버섯";

        final ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set(key,"1"); // redis set
        final String result_1 = valueOperations.get(key); // redis get

        System.out.println("result_1 = " + result_1);

        valueOperations.increment(key); // redis incr
        final String result_2 = valueOperations.get(key);

        System.out.println("result_2 = " + result_2);
    }
}