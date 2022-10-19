package com.example.team10searchengine.kordict.entity;

import com.example.team10searchengine.kordict.dto.KorDictSortResponseDto;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document("sort_keyword")
@Getter
public class KorDictMongo{
    @Id
    private String _id;

    private String keyword;
    private List<KorDictSortResponseDto> data;
    private LocalDateTime createdAt;

    public KorDictMongo(String keyword, List<KorDictSortResponseDto> data, LocalDateTime createdAt) {
        this.keyword = keyword;
        this.data = data;
        this.createdAt = createdAt;

    }

}
