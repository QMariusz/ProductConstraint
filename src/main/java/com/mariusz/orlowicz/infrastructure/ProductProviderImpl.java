package com.mariusz.orlowicz.infrastructure;

import com.mariusz.orlowicz.model.Product;
import org.springframework.web.client.RestTemplate;

public class ProductProviderImpl implements ProductProvider {

    @Override
    public Product fetch(String id) {
        String apiURL = "http://api.pl/product";
        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(apiURL, Product.class);
//        TODO add logging before and after request
        return new Product(id, "OR('VIP_ONLY',AND(NOT('FOR_RICH_PEOPLE'),'WITH_RELATIONS'))");
    }
}
