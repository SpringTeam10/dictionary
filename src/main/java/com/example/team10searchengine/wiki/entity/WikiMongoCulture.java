package com.example.team10searchengine.wiki.entity;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document("wiki_culture")
@Getter
public class WikiMongoCulture<T> {
    @Id
    private String _id;

    private String keyword;
    private List<T> data;
    private LocalDateTime createdAt;

    public WikiMongoCulture(String keyword, List<T> data, LocalDateTime createdAt) {
        this.keyword = keyword;
        this.data = data;
        this.createdAt = createdAt;

    }
}
