package com.example.mod4.adapter.repository;

import com.example.mod4.domain.CommentEntity;
import com.example.mod4.domain.NewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    List<CommentEntity> findByNews(NewEntity newEntity);
}
