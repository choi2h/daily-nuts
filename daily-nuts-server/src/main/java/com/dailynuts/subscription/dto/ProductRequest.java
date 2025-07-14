package com.dailynuts.subscription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    private int price;
}
