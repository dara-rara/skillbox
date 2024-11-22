package com.example.mod4.adapter.repository;

import com.example.mod4.domain.CategoryEntity;
import com.example.mod4.domain.CommentEntity;
import com.example.mod4.domain.NewEntity;
import com.example.mod4.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewRepository extends JpaRepository<NewEntity,Long> {
    List<NewEntity> findByUser(UserEntity user);
    List<NewEntity> findByCategory(CategoryEntity category);
}
