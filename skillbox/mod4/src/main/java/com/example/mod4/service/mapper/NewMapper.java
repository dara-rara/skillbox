package com.example.mod4.service.mapper;

import com.example.mod4.adapter.dto.request.NewsRequest;
import com.example.mod4.adapter.dto.response.CommentResponse;
import com.example.mod4.adapter.dto.response.NewListResponse;
import com.example.mod4.adapter.dto.response.NewSimpleResponse;
import com.example.mod4.domain.CategoryEntity;
import com.example.mod4.domain.NewEntity;
import com.example.mod4.domain.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class, UserMapper.class}
)
public interface NewMapper {
    default NewEntity toModel(UserEntity user, NewsRequest request, CategoryEntity category) {
        var news = new NewEntity();
        news.setText(request.getText());
        news.setUser(user);
        news.setCategory(category);
        news.setComments(0l);
        return news;
    }

    default NewEntity toUpdateModel(NewEntity newsItem, NewsRequest request, CategoryEntity category) {
        newsItem.setCategory(category);
        newsItem.setText(request.getText());
        return newsItem;
    }

    default NewSimpleResponse toResponse(NewEntity model, List<CommentResponse> commentResponses) {
        var response = new NewSimpleResponse();
        response.setId(model.getId());
        response.setText(model.getText());
        response.setUsername(model.getUser().getUsername());
        response.setCategory(model.getCategory().getName());
        response.setComments(commentResponses);
        return response;
    }

    default List<NewListResponse> toResponseList(List<NewEntity> models) {
        return models.stream().map(this::toResponseForList).toList();
    }

    private NewListResponse toResponseForList(NewEntity model) {
        var response = new NewListResponse();
        response.setId(model.getId());
        response.setText(model.getText());
        response.setUsername(model.getUser().getUsername());
        response.setCategory(model.getCategory().getName());
        response.setComments(model.getComments());
        return response;
    }

}
