package com.example.mod5.service;

import com.example.mod5.adapter.dto.request.CategoryRequest;
import com.example.mod5.adapter.repository.CategoryRepository;
import com.example.mod5.domain.CategoryEntity;
import com.example.mod5.mapper.CategoryMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static java.text.MessageFormat.format;

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

    public CategoryEntity getCategory(String name) {
        return categoryRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException(format("Category not found")));
    }

}
