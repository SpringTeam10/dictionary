package com.example.team10searchengine.kordict.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@RedisHash(value = "kordict", timeToLive = 60*60*24)
public class KorDictLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String keyword;

    public KorDictLog(String keyword){
        this.keyword = keyword;
    }
}