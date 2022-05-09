package com.mariusz.orlowicz.error;

public class FeignException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public FeignException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
