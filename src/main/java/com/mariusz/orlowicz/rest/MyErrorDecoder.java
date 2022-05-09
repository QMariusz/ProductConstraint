package com.mariusz.orlowicz.rest;

import com.mariusz.orlowicz.error.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class MyErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            return new FeignException(response.status(), response.reason());
        }
        if (response.status() >= 500) {
            return new RetryableException(500, response.reason(), response.request().httpMethod(), null, response.request());
        }
        return errorDecoder.decode(methodKey, response);
    }
}
