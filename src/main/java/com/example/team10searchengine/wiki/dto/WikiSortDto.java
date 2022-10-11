package com.example.team10searchengine.wiki.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WikiSortDto {
    private Long id;
    private String keyword;
    private String contents;
    private String img_url;
    private String detail_url;
    private String classification;
    private Integer num;
}
