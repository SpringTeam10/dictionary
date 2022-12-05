package com.example.team10searchengine.wiki.entity;

import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document("wiki_etc")
@Getter
public class WikiMongoEtc {
    @Id
    private String _id;

    private String keyword;
    private List<WikiSortResDto> data;
    private LocalDateTime createdAt;

    public WikiMongoEtc(String keyword, List<WikiSortResDto> data, LocalDateTime createdAt) {
        this.keyword = keyword;
        this.data = data;
        this.createdAt = createdAt;

    }
}
