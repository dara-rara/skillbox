package com.example.mod4.service.mapper;

import com.example.mod4.adapter.dto.request.CommentRequest;
import com.example.mod4.adapter.dto.response.CommentResponse;
import com.example.mod4.domain.CommentEntity;
import com.example.mod4.domain.NewEntity;
import com.example.mod4.domain.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewMapper.class}
)
public interface CommentMapper {

    default CommentEntity toModel(UserEntity user, NewEntity news, CommentRequest request) {
        var comment = new CommentEntity();
        comment.setText(request.getText());
        comment.setUser(user);
        comment.setNews(news);
        return comment;
    }

    default CommentEntity toUpdateModel(CommentEntity comment, CommentRequest request) {
        comment.setText(request.getText());
        return comment;
    };

    default List<CommentResponse> toResponseList(List<CommentEntity> models) {
        return models.stream().map(this::toResponseForList).toList();
    };

    default CommentResponse toResponse(CommentEntity model) {
        return toResponseForList(model);
    }

    private CommentResponse toResponseForList(CommentEntity model) {
        var response = new  CommentResponse();
        response.setId(model.getId());
        response.setText(model.getText());
        response.setUsername(model.getUser().getUsername());
        return response;
    }

}
