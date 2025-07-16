package com.dailynuts.post.service;

import com.dailynuts.post.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDto> getAllCategories();
}
