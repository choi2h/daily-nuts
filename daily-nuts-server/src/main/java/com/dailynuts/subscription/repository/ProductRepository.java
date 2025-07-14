package com.dailynuts.subscription.repository;

import com.dailynuts.subscription.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
