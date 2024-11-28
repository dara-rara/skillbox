package com.example.mod5.adapter.web;

import com.example.mod5.adapter.dto.request.BookRequest;
import com.example.mod5.adapter.dto.request.CategoryRequest;
import com.example.mod5.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/list")
    public ResponseEntity<?> findAll(@RequestBody CategoryRequest request) {
        return ResponseEntity.status(OK).body(bookService.findAll(request.getName()));
    }

    @GetMapping("/name/{name}/author/{author}")
    public ResponseEntity<?> findByNameAndAuthor(@PathVariable String name,
                                                    @PathVariable String author) {
        try {
            return ResponseEntity.status(OK).body(bookService.findByNameAndAuthor(name, author));
        } catch (Exception e) {
            return ResponseEntity.status(NO_CONTENT).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid BookRequest request) {
        try {
            return ResponseEntity.status(CREATED).body(bookService.create(request));
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid BookRequest request) {
        try {
            return ResponseEntity.status(OK).body(bookService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.status(NO_CONTENT).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            bookService.deleteById(id);
            return ResponseEntity.status(OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(NO_CONTENT).build();
        }
    }

}
