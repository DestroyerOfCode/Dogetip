package com.doge.tip.exception.common;

public class APIRequestException extends RuntimeException{

    public APIRequestException() {
        super();
    }

    public APIRequestException(String message) {
        super(message);
    }

    public APIRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
