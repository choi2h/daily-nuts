package com.dailynuts.subscription.dto;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductsResponseDto {
    private List<ProductResponseDto> products;

    public ProductsResponseDto() {
        products = new ArrayList<>();
    }

    public void addProduct(ProductResponseDto product) {
        products.add(product);
    }
}
