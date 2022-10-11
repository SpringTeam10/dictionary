package com.example.team10searchengine.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    @AllArgsConstructor
    @Getter
    public static class Error {
        private String code;
        private String message;
    }


    public static <T> ResponseDto<T> success(T data) {

        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<>(false, null, new Error(code, message));
    }
}