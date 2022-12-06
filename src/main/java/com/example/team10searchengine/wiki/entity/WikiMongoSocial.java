package com.example.team10searchengine.wiki.entity;

import com.example.team10searchengine.wiki.dto.WikiSortResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Document("wiki_social")
@Getter
public class WikiMongoSocial {
    @Id
    private String _id;

    private String keyword;
    private List<WikiSortResDto> data;
    private LocalDateTime createdAt;

    public WikiMongoSocial(String keyword, List<WikiSortResDto> data, LocalDateTime createdAt) {
        this.keyword = keyword;
        this.data = data;
        this.createdAt = createdAt;

    }
}
