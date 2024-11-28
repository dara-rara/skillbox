package com.example.mod5.mapper;

import com.example.mod5.adapter.dto.request.CategoryRequest;
import com.example.mod5.domain.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BookMapper.class}

)
public interface CategoryMapper {
    CategoryEntity toModel(CategoryRequest request);
}
