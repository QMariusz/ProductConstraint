package com.mariusz.orlowicz.rest;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.mariusz.orlowicz")
public class ProjectConfig {
}
