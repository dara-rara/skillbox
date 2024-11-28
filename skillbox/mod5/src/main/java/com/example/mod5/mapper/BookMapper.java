package com.example.mod5.mapper;

import com.example.mod5.adapter.dto.request.BookRequest;
import com.example.mod5.adapter.dto.response.BookResponse;
import com.example.mod5.domain.BookEntity;
import com.example.mod5.domain.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookMapper {

    default BookEntity toModel(BookRequest request, CategoryEntity category) {
        var book = new BookEntity();
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        book.setCategory(category);
        return book;
    };

    default BookEntity toUpdatedModel(BookEntity book, BookRequest request, CategoryEntity category) {
        book.setName(request.getName());
        book.setAuthor(request.getAuthor());
        book.setCategory(category);
        return book;
    }

    default BookResponse toResponse(BookEntity model) {
        var response = new BookResponse();
        response.setId(model.getId());
        response.setAuthor(model.getAuthor());
        response.setName(model.getName());
        response.setCategory(model.getCategory().getName());
        return response;
    };

    default List<BookResponse> toResponseList(List<BookEntity> models) {
        return models.stream().map(this::toResponse).toList();
    };
}
