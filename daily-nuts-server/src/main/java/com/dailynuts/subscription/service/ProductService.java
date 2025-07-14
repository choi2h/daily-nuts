package com.dailynuts.subscription.service;

import com.dailynuts.subscription.dto.ProductRequest;
import com.dailynuts.subscription.dto.ProductResponse;
import com.dailynuts.subscription.dto.ProductsResponse;

public interface ProductService {
    Long saveProduct(ProductRequest productRequest);
    ProductResponse getProductById(Long id);
    ProductsResponse getAllProducts();
}
