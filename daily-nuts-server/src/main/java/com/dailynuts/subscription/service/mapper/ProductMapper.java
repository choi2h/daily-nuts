package com.dailynuts.subscription.service.mapper;

import com.dailynuts.subscription.dto.ProductRequest;
import com.dailynuts.subscription.dto.ProductResponse;
import com.dailynuts.subscription.dto.ProductsResponse;
import com.dailynuts.subscription.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponse getResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }

    public Product getProduct(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    public ProductsResponse getProductsResponse(List<Product> products) {
        ProductsResponse response = new ProductsResponse();
        products.forEach(product -> {
            ProductResponse productResponse = getResponse(product);
            response.addProduct(productResponse);
        });

        return response;
    }

}
