package com.dailynuts.subscription.dto;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductsResponse {
    private List<ProductResponse> products;

    public ProductsResponse() {
        products = new ArrayList<>();
    }

    public void addProduct(ProductResponse product) {
        products.add(product);
    }
}
