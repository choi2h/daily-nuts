package com.dailynuts.subscription.service;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.subscription.dto.ProductRequestDto;
import com.dailynuts.subscription.dto.ProductResponseDto;
import com.dailynuts.subscription.dto.ProductsResponseDto;
import com.dailynuts.subscription.entity.Product;
import com.dailynuts.subscription.repository.ProductRepository;
import com.dailynuts.subscription.service.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Long saveProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.getProduct(productRequestDto);
        product = productRepository.save(product);
        return product.getId();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.PRODUCT_NOT_EXIST));

        return productMapper.getResponse(product);
    }

    @Override
    public ProductsResponseDto getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.getProductsResponse(products);
    }
}
