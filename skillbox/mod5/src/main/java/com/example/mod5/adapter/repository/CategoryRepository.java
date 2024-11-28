package com.example.mod5.adapter.repository;


import com.example.mod5.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Boolean existsByName(String name);
    Optional<CategoryEntity> findByName(String name);
}
