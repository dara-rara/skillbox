package com.example.mod4.service.aop;

import com.example.mod4.adapter.repository.CommentRepository;
import com.example.mod4.adapter.repository.NewRepository;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OwnershipAspect {
    private final CommentRepository commentRepository;
    private final NewRepository newRepository;

    @Around("@annotation(checkOwnership)")
    public Object checkOwnership(ProceedingJoinPoint joinPoint, CheckOwnership checkOwnership) throws Throwable {
        Long commentId = (Long) joinPoint.getArgs()[0]; // Id - первый аргумент метода
        UserEntity user = (UserEntity) joinPoint.getArgs()[1]; // user - второй аргумент метода

        var repository = checkOwnership.repositoryClass();
        if (repository.equals("CommentRepository")) {
            var comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
            if (!comment.getUser().getId_user().equals(user.getId_user())) {
                throw new AccessDeniedException("You cannot edit or delete this comment");
            }
        } else if (repository.equals("NewRepository")) {
            var news = newRepository.findById(commentId)
                    .orElseThrow(() -> new ResourceNotFoundException("News not found"));
            if (!news.getUser().getId_user().equals(user.getId_user())) {
                throw new AccessDeniedException("You cannot edit or delete this news");
            }
        } else {
            throw new IllegalArgumentException("Unknown repository class");
        }
        return joinPoint.proceed(); // продолжаем выполнение метода, если проверки пройдены
    }
}
