package com.dailynuts.subscription.service.mapper;

import com.dailynuts.subscription.dto.ProductRequestDto;
import com.dailynuts.subscription.dto.ProductResponseDto;
import com.dailynuts.subscription.dto.ProductsResponseDto;
import com.dailynuts.subscription.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponseDto getResponse(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public Product getProduct(ProductRequestDto request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    public ProductsResponseDto getProductsResponse(List<Product> products) {
        ProductsResponseDto response = new ProductsResponseDto();
        products.forEach(product -> {
            ProductResponseDto productResponseDto = getResponse(product);
            response.addProduct(productResponseDto);
        });

        return response;
    }

}
