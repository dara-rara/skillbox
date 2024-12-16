package com.example.mod4.service.mapper;

import com.example.mod4.adapter.dto.request.CategoryRequest;
import com.example.mod4.adapter.dto.response.CategoryResponse;
import com.example.mod4.domain.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewMapper.class}
)
public interface CategoryMapper {

    CategoryEntity toModel(CategoryRequest request);
    List<CategoryResponse> toResponseList(List<CategoryEntity> models);

    default CategoryEntity toUpdate(CategoryEntity category, CategoryRequest request) {
        category.setName(request.getName());
        return category;
    }
}
