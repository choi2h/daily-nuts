package com.dailynuts.subscription.controller;

import com.dailynuts.subscription.dto.ProductRequest;
import com.dailynuts.subscription.dto.ProductResponse;
import com.dailynuts.subscription.dto.ProductsResponse;
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
    public ResponseEntity<Long> saveProduct(@RequestBody @Valid ProductRequest productRequest) {
        Long id = productService.saveProduct(productRequest);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductsResponse> getAllProducts() {
        ProductsResponse response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }
}
