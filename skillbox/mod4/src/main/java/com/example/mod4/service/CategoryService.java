package com.example.mod4.service;

import com.example.mod4.adapter.dto.request.CategoryRequest;
import com.example.mod4.adapter.dto.response.CategoryResponse;
import com.example.mod4.adapter.repository.CategoryRepository;
import com.example.mod4.domain.CategoryEntity;
import com.example.mod4.service.error.ResourceNotFoundException;
import com.example.mod4.service.mapper.CategoryMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryEntity createCategory(CategoryRequest request) {
        if (!categoryRepository.existsByName(request.getName()))
            return categoryRepository.save(categoryMapper.toModel(request));
        else return categoryRepository.findByName(request.getName()).get();
    }

    public void createCategoryManually(CategoryRequest request) {
        if (!categoryRepository.existsByName(request.getName()))
            categoryRepository.save(categoryMapper.toModel(request));
    }

    public void updateCategory(Long id, CategoryRequest request) {
        categoryRepository.save(categoryMapper.toUpdate(getCategory(id),request));
    }

    public void deleteCategory(Long id) {
        categoryRepository.delete(getCategory(id));
    }

    public CategoryEntity getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category not found"));
    }

    public List<CategoryResponse> findAll() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }
}
