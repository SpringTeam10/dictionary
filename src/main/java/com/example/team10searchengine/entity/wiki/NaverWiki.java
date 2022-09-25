package com.example.team10searchengine.entity.wiki;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name="naver_wikipedia")
public class NaverWiki {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String keyword;

    @Column
    String contents;

    @Column
    String img_url;

    @Column
    String detail_url;

}
