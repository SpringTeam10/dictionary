package com.example.team10searchengine.kordict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name="dictionary")
public class KorDict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
