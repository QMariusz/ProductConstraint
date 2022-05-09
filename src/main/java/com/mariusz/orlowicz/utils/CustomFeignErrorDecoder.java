package com.mariusz.orlowicz.utils;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (HttpStatus.valueOf(response.status()).is4xxClientError()) {
            return new CustomFeignException(response.status(), response.reason());
        }
        if (HttpStatus.valueOf(response.status()).is5xxServerError()) {
            return new RetryableException(response.status(), response.reason(), response.request().httpMethod(), null, response.request());
        }
        return errorDecoder.decode(methodKey, response);
    }
}
