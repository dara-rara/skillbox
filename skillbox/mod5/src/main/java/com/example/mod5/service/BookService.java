package com.example.mod5.service;

import com.example.mod5.adapter.dto.request.BookRequest;
import com.example.mod5.adapter.dto.request.CategoryRequest;
import com.example.mod5.adapter.dto.response.BookResponse;
import com.example.mod5.adapter.repository.BookRepository;
import com.example.mod5.domain.BookEntity;
import com.example.mod5.mapper.BookMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.mod5.config.properties.AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY;
import static com.example.mod5.config.properties.AppCacheProperties.CacheNames.BOOKS_BY_NAME_AND_AUTHOR;

@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final BookMapper bookMapper;

    @Cacheable(value = BOOKS_BY_CATEGORY, key="#category")
    public List<BookResponse> findAll(String category) {
        return bookMapper.toResponseList(bookRepository.findByCategory(categoryService.getCategory(category)));
    }

    @Cacheable(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#name + #author")
    public BookResponse findByNameAndAuthor(String name, String author) {
        return bookMapper.toResponse(getNameAndAuthor(name, author));
    }

    @Caching(evict = {
            @CacheEvict(value = BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#request.name + #request.author")
    })
    public BookResponse create(BookRequest request) {
        if(bookRepository.getBookEntityByNameAndAuthor(request.getName(), request.getAuthor()).isPresent()) {
            throw new EntityExistsException("Book already exists");
        }
        var category = categoryService.
                createCategory(new CategoryRequest(request.getCategory()));
        var book = bookMapper.toModel(request, category);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Caching(evict = {
            @CacheEvict(value = BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#id", beforeInvocation = true)
    })
    public BookResponse update(Long id, BookRequest request) {
        var book = getByBook(id);
        var category = categoryService.
                createCategory(new CategoryRequest(request.getCategory()));
        var updatedBook = bookMapper.toUpdatedModel(book, request, category);
        return bookMapper.toResponse(bookRepository.save(updatedBook));
    }

    @Caching(evict = {
            @CacheEvict(value = BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#id", beforeInvocation = true)
    })
    public void deleteById(Long id) {
        bookRepository.delete(getByBook(id));
    }

    private BookEntity getNameAndAuthor(String name, String author) {
        return bookRepository.getBookEntityByNameAndAuthor(name, author)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    private BookEntity getByBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

}
