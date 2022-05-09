package com.mariusz.orlowicz.utils;

import lombok.Getter;

@Getter
public class CustomApplicationResponse {

    private final String errorMessage;

    public CustomApplicationResponse(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
