package com.mariusz.orlowicz.utils;

import feign.RetryableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomFeignException.class)
    protected ResponseEntity<CustomApplicationResponse> handleCustomFeignException(CustomFeignException ex, WebRequest request) {
        return ResponseEntity.status(ex.getErrorCode())
                .body(new CustomApplicationResponse(ex.getMessage()));
    }

    @ExceptionHandler(RetryableException.class)
    protected ResponseEntity<CustomApplicationResponse> handleRetryableException(RetryableException ex, WebRequest request) {
        return ResponseEntity.status(ex.status())
                .body(new CustomApplicationResponse(ex.getMessage()));
    }
}