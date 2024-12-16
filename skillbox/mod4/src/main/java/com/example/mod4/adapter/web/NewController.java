package com.example.mod4.adapter.web;

import com.example.mod4.adapter.dto.request.NewsRequest;
import com.example.mod4.adapter.dto.response.MessageResponse;
import com.example.mod4.adapter.dto.response.NewListResponse;
import com.example.mod4.adapter.dto.response.UserResponse;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.NewService;
import com.example.mod4.service.aop.UserAccessCheck;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/api/new/", produces = APPLICATION_JSON_VALUE)
public class NewController {
    private final NewService newService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(newService.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> createNews(@AuthenticationPrincipal UserEntity user, @RequestBody NewsRequest request) {
        return ResponseEntity.status(201).body(newService.createNew(user, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @AuthenticationPrincipal UserEntity user, @RequestBody NewsRequest request) {
        return ResponseEntity.status(200).body(newService.editNew(id, user, request));
    }

    @DeleteMapping("/{id}")
    @UserAccessCheck(action = "DELETE")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        newService.deleteNew(id);
        return ResponseEntity.status(200).body(new MessageResponse("Delete new: " + id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNews(@PathVariable Long id) {
        return ResponseEntity.status(200).body(newService.findByNews(id));
    }

    @GetMapping("/filterCat/{id}")
    public ResponseEntity<?> getFilterCategory(@PathVariable Long id) {
        return ResponseEntity.status(200).body(newService.findByCategory(id));
    }

    @GetMapping("/filterUser/{id}")
    public ResponseEntity<?> getFilterUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(newService.findByUser(id));
    }

}
