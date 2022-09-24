package com.example.team10searchengine.entity.weke;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Weke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String word;

    @Column
    String content;

    @Column
    String image;

    @Column
    String detailUrl;

    String keyword;
    public Weke(String word, String image, String content, String detailUrl, String keyword){
        this.word = word;
        this.image = image;
        this.content = content;
        this.detailUrl = detailUrl;
        this.keyword = keyword;
    }
}
