package com.dailynuts.subscription.service;

import com.dailynuts.subscription.dto.ProductRequestDto;
import com.dailynuts.subscription.dto.ProductResponseDto;
import com.dailynuts.subscription.dto.ProductsResponseDto;

public interface ProductService {
    Long saveProduct(ProductRequestDto productRequestDto);
    ProductResponseDto getProductById(Long id);
    ProductsResponseDto getAllProducts();
}
