package com.mariusz.orlowicz.rest;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CustomFeignRetry implements Retryer {

    @Value("${application.feign.retry-max-attempts}")
    private int retryMaxAttempts;
    @Value("${application.feign.retry-interval}")
    private long retryInterval;

    private int attempt = 1;

    public CustomFeignRetry(int retryMaxAttempts, long retryInterval) {
        this.retryMaxAttempts = retryMaxAttempts;
        this.retryInterval = retryInterval;
    }

    @Override
    public void continueOrPropagate(RetryableException retryableException) {
        if (attempt++ >= retryMaxAttempts) {
            throw retryableException;
        }

        try {
            Thread.sleep(retryInterval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new CustomFeignRetry(retryMaxAttempts, retryInterval);
    }
}
