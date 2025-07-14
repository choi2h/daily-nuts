package com.dailynuts.subscription.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Builder
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
