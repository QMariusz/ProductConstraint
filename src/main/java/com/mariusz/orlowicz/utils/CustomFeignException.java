package com.mariusz.orlowicz.utils;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    private final int errorCode;

    public CustomFeignException(final int errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
