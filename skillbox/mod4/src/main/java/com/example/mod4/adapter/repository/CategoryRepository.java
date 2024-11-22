package com.example.mod4.adapter.repository;

import com.example.mod4.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    Boolean existsByName(String name);
    Optional<CategoryEntity> findByName(String name);
}
