package com.example.mod4.service;

import com.example.mod4.adapter.dto.request.CategoryRequest;
import com.example.mod4.adapter.dto.request.NewsRequest;
import com.example.mod4.adapter.dto.response.CommentResponse;
import com.example.mod4.adapter.dto.response.NewListResponse;
import com.example.mod4.adapter.dto.response.NewSimpleResponse;
import com.example.mod4.adapter.repository.CommentRepository;
import com.example.mod4.adapter.repository.NewRepository;
import com.example.mod4.service.aop.CheckOwnership;
import com.example.mod4.domain.NewEntity;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.mapper.CommentMapper;
import com.example.mod4.service.error.ResourceNotFoundException;
import com.example.mod4.service.mapper.NewMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NewService {
    private final NewMapper newMapper;
    private final NewRepository newRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    public List<NewListResponse> findAll() {
        return newMapper.toResponseList(newRepository.findAll());
    }

    public NewEntity createNew(UserEntity user, NewsRequest request) {
        var category = categoryService.createCategory(new CategoryRequest(request.getCategory()));
        return newRepository.save(newMapper.toModel(user, request, category));
    }

    @CheckOwnership(repositoryClass = "NewRepository")
    public void deleteNew(Long newsId) {
        var news = newRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News not found"));
        newRepository.delete(news);
    }

    @CheckOwnership(repositoryClass = "NewRepository")
    public NewEntity editNew(Long newsId, UserEntity user, NewsRequest newsDetails) {
        var news = newRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News not found"));
        var category = categoryService.createCategory(new CategoryRequest(newsDetails.getCategory()));
        return newRepository.save(newMapper.toUpdateModel(news, newsDetails, category));
    }

    public NewSimpleResponse findByNews(Long newsId) {
        var news = getByNews(newsId);
        var comments = findAllByNew(news);
        return newMapper.toResponse(news, comments);
    }
    public List<NewListResponse> findByUser(Long userId) {
        var user = userService.getByUserId(userId);
        return newMapper.toResponseList(newRepository.findByUser(user));
    }

    public List<NewListResponse> findByCategory(Long catId) {
        var category = categoryService.getCategory(catId);
        return newMapper.toResponseList(newRepository.findByCategory(category));
    }

    public NewEntity getByNews(Long newsId) {
        return newRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News not found"));
    }

    private List<CommentResponse> findAllByNew(NewEntity newEntity) {
        var list = commentRepository.findByNews(newEntity);
        return commentMapper.toResponseList(list);
    }
}
