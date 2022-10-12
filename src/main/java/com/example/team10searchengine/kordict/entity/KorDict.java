package com.example.team10searchengine.kordict.entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Getter
@Setter
@IdClass(KorDictPrimary.class)
@Entity(name="kor_dict")
public class KorDict {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Id
    @Column
    private String word;
    @Column
    private String type;
    @Column
    private String isUnique;
    @Column
    private String pronunciation;
    @Column
    private String part;
    @Column
    private String meaning;
    @Column
    private String example;
    @Column
    private String field;
    @Column
    private String proverb;
    @Column
    private String idiom;

    @Column
    private String classification;

    public KorDict(String word, String type, String isUnique, String pronunciation, String part, String meaning, String example, String field, String proverb, String idiom, String classification) {
        this.word = word;
        this.type = type;
        this.isUnique = isUnique;
        this.pronunciation = pronunciation;
        this.part = part;
        this.meaning = meaning;
        this.example = example;
        this.field = field;
        this.proverb = proverb;
        this.idiom = idiom;
        this.classification = classification;
    }
}
