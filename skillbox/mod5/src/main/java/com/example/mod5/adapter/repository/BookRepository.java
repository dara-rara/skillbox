package com.example.mod5.adapter.repository;

import com.example.mod5.domain.BookEntity;
import com.example.mod5.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> getBookEntityByNameAndAuthor(String name, String author);
    List<BookEntity> findByCategory(CategoryEntity category);
}
