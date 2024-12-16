package com.example.mod4.service;

import com.example.mod4.adapter.dto.request.CommentRequest;
import com.example.mod4.adapter.dto.response.CommentResponse;
import com.example.mod4.adapter.repository.CommentRepository;
import com.example.mod4.adapter.repository.NewRepository;
import com.example.mod4.domain.CategoryEntity;
import com.example.mod4.domain.CommentEntity;
import com.example.mod4.service.aop.CheckOwnership;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.error.ResourceNotFoundException;
import com.example.mod4.service.mapper.CommentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewService newService;
    private final NewRepository newRepository;

    public CommentEntity createComment(UserEntity user, CommentRequest request, Long newId) {
        var newsItem = newService.getByNews(newId);
        newsItem.setComments(newsItem.getComments() + 1);
        var comment = commentMapper.toModel(user, newsItem, request);
        commentRepository.save(comment);
        newRepository.save(newsItem);
        return comment;
    }

    public CommentEntity getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }

    @CheckOwnership(repositoryClass = "CommentRepository")
    public void deleteComment(Long commentId) {
        var comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    @CheckOwnership(repositoryClass = "CommentRepository")
    public CommentEntity editComment(Long commentId, CommentRequest commentRequest) {
        var comment = getComment(commentId);
        commentRepository.save(commentMapper.toUpdateModel(comment, commentRequest));
        return comment;
    }
}
