package com.example.team10searchengine.wiki.entity;

import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document("wiki_history")
@Getter
public class WikiMongoHistory<T> {
    @Id
    private String _id;

    private String keyword;
    private List<T> data;
    private LocalDateTime createdAt;

    public WikiMongoHistory(String keyword, List<T> data, LocalDateTime createdAt) {
        this.keyword = keyword;
        this.data = data;
        this.createdAt = createdAt;

    }
}
