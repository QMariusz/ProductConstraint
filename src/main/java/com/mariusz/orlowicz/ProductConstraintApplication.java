package com.mariusz.orlowicz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mariusz.orlowicz")
public class ProductConstraintApplication {

	public static void main(String[] args) {SpringApplication.run(ProductConstraintApplication.class, args);}

}
