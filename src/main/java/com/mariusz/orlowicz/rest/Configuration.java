package com.mariusz.orlowicz.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class Configuration {

    @Value("${application.rest-template.timeout}")
    private int timeoutInSeconds;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
    {
        return restTemplateBuilder
                .setConnectTimeout(Duration.of(timeoutInSeconds, ChronoUnit.SECONDS))
                .errorHandler(new RestTemplateResponseErrorHandler())
//                .setReadTimeout()
                .build();
    }

//    @Bean
//    RestTemplateCustomizer retryRestTemplateCustomizer() {
//        return restTemplate -> restTemplate.getInterceptors().add((request, body, execution) -> {
//
//            RetryTemplate retryTemplate = new RetryTemplate();
//            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
//            try {
//                return retryTemplate.execute(context -> {
//                    System.out.println("start retrying ....");
//                    return execution.execute(request, body);
//                });
//            } catch (Throwable throwable) {
//                throw new RuntimeException(throwable);
//            }
//        });
//    }
}
