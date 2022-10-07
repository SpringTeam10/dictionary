package com.example.team10searchengine.entity.kordict.querydslrepo;

import com.example.team10searchengine.entity.kordict.KorDict;
import com.example.team10searchengine.entity.kordict.QKorDict;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.team10searchengine.entity.kordict.QKorDict.korDict;

@Repository
@RequiredArgsConstructor
public class KorDictCustomRepositoryImpl implements KorDictCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

//    @Override
//    public List<KorDict> findByWordUsingQuerydsl(String word) {
//        QKorDict korDict = QKorDict.korDict;
//        return jpaQueryFactory.selectFrom(korDict)
//                .where(fullTextWord(word))
////                .orderBy(korDict.word.like(word).asc(),korDict.word.asc())  //에러
//                .fetch();
//    }
    @Override
    public List<KorDict> findByWordUsingQuerydsl(String word, Long korDictId, Pageable pageable) {
        QKorDict korDict = QKorDict.korDict;
        return jpaQueryFactory.select(Projections.fields(KorDict.class,
                        korDict.word,
                        korDict.id,
                        korDict.isUnique,
                        korDict.pronunciation,
                        korDict.part,
                        korDict.meaning,
                        korDict.example,
                        korDict.field,
                        korDict.proverb,
                        korDict.idiom,
                        korDict.classification))
                .from(korDict)
                .where(
                        ltKorDictId(korDictId),
                        fullTextWord(word)
                )
                .orderBy(korDict.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression fullTextWord(String word) {
        NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
                "function('match',{0},{1})", korDict.word, "+" + word + "*");
        return booleanTemplate.gt(0);
    }
    private BooleanExpression ltKorDictId(Long korDictId) {
        if (korDictId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }

        return korDict.id.lt(korDictId);
    }
}