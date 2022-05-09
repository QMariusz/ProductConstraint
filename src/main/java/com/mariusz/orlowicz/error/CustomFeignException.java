package com.mariusz.orlowicz.error;

public class CustomFeignException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public CustomFeignException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
