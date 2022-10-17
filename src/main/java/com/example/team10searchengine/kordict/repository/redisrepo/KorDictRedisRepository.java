package com.example.team10searchengine.kordict.repository.redisrepo;

import com.example.team10searchengine.kordict.entity.KorDictLog;
import org.springframework.data.repository.CrudRepository;

public interface KorDictRedisRepository extends CrudRepository<KorDictLog,Long> {
}
