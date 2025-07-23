package com.dailynuts.subscription.controller;

import com.dailynuts.subscription.dto.ProductRequestDto;
import com.dailynuts.subscription.dto.ProductResponseDto;
import com.dailynuts.subscription.dto.ProductsResponseDto;
import com.dailynuts.subscription.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Long> saveProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Long id = productService.saveProduct(productRequestDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id) {
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductsResponseDto> getAllProducts() {
        ProductsResponseDto response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }
}
