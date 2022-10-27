package com.example.team10searchengine.kordict.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Getter
@Setter
@IdClass(KorDictPrimary.class)
@Entity(name="kor_dict")
public class KorDict implements Serializable {
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
}
