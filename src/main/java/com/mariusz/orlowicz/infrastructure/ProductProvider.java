package com.mariusz.orlowicz.infrastructure;

import com.mariusz.orlowicz.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products", url = "${application.products.api-url}")
public interface ProductProvider {

    @GetMapping("/products/{productId}")
    Product fetch(@PathVariable String productId);
}
