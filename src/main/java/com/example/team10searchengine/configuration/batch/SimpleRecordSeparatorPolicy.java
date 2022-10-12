package com.example.team10searchengine.configuration.batch;

import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

public class SimpleRecordSeparatorPolicy implements RecordSeparatorPolicy {
    @Override
    public boolean isEndOfRecord(String record) {
        return false;
    }

    @Override
    public String postProcess(String record) {
        return null;
    }

    @Override
    public String preProcess(String record) {
        return null;
    }
}
