package com.example.newestlinen.exception;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException{
    private String errorCode;

    public RequestException(String message) { super(message); }

    public RequestException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
