package com.example.mod4.service.aop;

import com.example.mod4.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAccessAspect {
    private final UserService userService;

    @Around("@annotation(UserAccessCheck)")
    public Object checkUserAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        UserAccessCheck annotation = signature.getMethod().getAnnotation(UserAccessCheck.class);
        String action = annotation.action();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String[] roles = authentication.getAuthorities().stream()
                .map(Object::toString)
                .toArray(String[]::new);

        // Получаем ID пользователя из аргументов метода
        Object[] args = joinPoint.getArgs();
        Long userId = null;
        for (Object arg : args) {
            if (arg instanceof Long) {
                userId = (Long) arg;
                break;
            }
        }

        // Проверка прав доступа
        if (!hasAccess(action, roles, username, userId)) {
            throw new AccessDeniedException("Access denied");
        }

        return joinPoint.proceed();
    }

    private boolean hasAccess(String action, String[] roles, String username, Long userId) {
        switch (action) {
            case "READ":
                // Проверка на получение информации о пользователе
                if (Arrays.asList(roles).contains("ROLE_ADMIN") ||
                        Arrays.asList(roles).contains("ROLE_MODERATOR")) {
                    return true;
                }
                if (Arrays.asList(roles).contains("ROLE_USER") && userId != null && isUserSelf(username, userId)) {
                    return true;
                }
                break;

            case "UPDATE":
                // Проверка на обновление информации о пользователе
                if (Arrays.asList(roles).contains("ROLE_ADMIN") ||
                        Arrays.asList(roles).contains("ROLE_MODERATOR")) {
                    return true;
                }
                if (Arrays.asList(roles).contains("ROLE_USER") && userId != null && isUserSelf(username, userId)) {
                    return true;
                }
                break;

            case "UPDATE_COMMENT":
                if (isUserSelf(username, userId)) return true;
                break;

            case "DELETE":
                // Проверка на удаление пользователя
                if (Arrays.asList(roles).contains("ROLE_ADMIN") ||
                        Arrays.asList(roles).contains("ROLE_MODERATOR")) {
                    return true;
                }
                if (Arrays.asList(roles).contains("ROLE_USER") && userId != null && isUserSelf(username, userId)) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean isUserSelf(String username, Long userId) {
        // Логика для проверки, является ли текущий пользователь владельцем профиля
         var user = userService.getByUserId(userId);
         return user.getUsername().equals(username);
    }
}
