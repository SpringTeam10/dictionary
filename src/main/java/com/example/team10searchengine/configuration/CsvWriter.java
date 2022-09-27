package com.example.team10searchengine.configuration;

import com.example.team10searchengine.entity.kordict.KorDict;
import com.example.team10searchengine.entity.kordict.KorDictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CsvWriter implements ItemWriter<KorDict> {

    private final KorDictRepository korDictRepository;
//    private final WekeRepository wekeRepository;

    @Override
    public void write(List<? extends KorDict> list) {
        List<KorDict> korDictList = new ArrayList<>();

        list.forEach(getKorDict -> {
            KorDict korDict = new KorDict();
            korDict.setWord(getKorDict.getWord());
            korDict.setType(getKorDict.getType());
            korDict.setIsUnique(getKorDict.getIsUnique());
            korDict.setPronunciation(getKorDict.getPronunciation());
            korDict.setPart(getKorDict.getPart());
            korDict.setMeaning(getKorDict.getMeaning());
            korDict.setExample(getKorDict.getExample());
            korDict.setField(getKorDict.getField());
            korDict.setProverb(getKorDict.getProverb());
            korDict.setIdiom(getKorDict.getIdiom());

            korDictList.add(korDict);

        });
        korDictRepository.saveAll(korDictList);
    }
}
