package com.mariusz.orlowicz.infrastructure;

import com.mariusz.orlowicz.model.Product;

public interface ProductProvider {

    Product fetch(String id);
}
