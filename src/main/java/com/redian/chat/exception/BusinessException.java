package com.redian.chat.exception;

public class BusinessException extends RuntimeException {

    protected int code = 400;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, int code, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
