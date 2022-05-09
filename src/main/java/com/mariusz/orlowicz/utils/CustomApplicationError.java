package com.mariusz.orlowicz.utils;

import lombok.Getter;

@Getter
public class CustomApplicationError {

    private final String errorMessage;

    public CustomApplicationError(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
