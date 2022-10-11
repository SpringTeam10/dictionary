package com.example.team10searchengine.configuration.batch;

import com.example.team10searchengine.kordict.entity.KorDict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CsvReader {
    @Bean
    public FlatFileItemReader<KorDict> csvFileItemReader() {

        // file read
        FlatFileItemReader<KorDict> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("/csv/KorDict.csv"));
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setEncoding("UTF-8");

        // read하는 데이터를 내부적으로 LineMapper을 통해 Mapping
        DefaultLineMapper<KorDict> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        // csv 파일에서 구분자 설정
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        // 각각의 데이터 이름 설정 - 엔티티 필드의 이름과 동일하게 설정
        delimitedLineTokenizer.setNames("word","type","isUnique","pronunciation",
                "part","meaning","example","field","proverb","idiom");

        // csv 파일의 컬럼과 불일치 허용
        delimitedLineTokenizer.setStrict(false);


        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드 */
        BeanWrapperFieldSetMapper<KorDict> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(KorDict.class);

        defaultLineMapper.setFieldSetMapper((beanWrapperFieldSetMapper));

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }

}
