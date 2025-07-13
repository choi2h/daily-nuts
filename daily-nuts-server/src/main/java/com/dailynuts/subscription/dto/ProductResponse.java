package com.dailynuts.subscription.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
}
