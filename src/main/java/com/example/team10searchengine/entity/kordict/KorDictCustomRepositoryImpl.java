package com.example.team10searchengine.entity.kordict;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.team10searchengine.entity.kordict.QKorDict.korDict;

@Repository
@RequiredArgsConstructor
public class KorDictCustomRepositoryImpl implements KorDictCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<KorDict> findByWordUsingQuerydsl(String word) {
        QKorDict korDict = QKorDict.korDict;

        return jpaQueryFactory.selectFrom(korDict)
                .where(fullTextWord(word))
//                .orderBy(korDict.word.like(word).desc(),korDict.word.asc())  에러
                .fetch();
    }

    private BooleanExpression fullTextWord(String word) {
        NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
                "function('match',{0},{1})", korDict.word, "+" + word + "*");
        return booleanTemplate.gt(0);
    }
}