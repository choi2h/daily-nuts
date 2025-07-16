package com.dailynuts.post.service;

import com.dailynuts.post.dto.CategoryResponseDto;
import com.dailynuts.post.entity.Category;
import com.dailynuts.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> responseDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            responseDtoList.add(new CategoryResponseDto(category.getId(), category.getName()));
        }

        return responseDtoList;
    }
}
