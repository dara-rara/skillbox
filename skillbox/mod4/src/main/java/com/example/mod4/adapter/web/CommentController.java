package com.example.mod4.adapter.web;

import com.example.mod4.adapter.dto.request.CommentRequest;
import com.example.mod4.adapter.dto.request.NewsRequest;
import com.example.mod4.adapter.dto.response.MessageResponse;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/api/com/", produces = APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add/{id}")
    public ResponseEntity<?> createComment(@PathVariable Long id, @AuthenticationPrincipal UserEntity user,
                                           @RequestBody CommentRequest request) {
        return ResponseEntity.status(201).body(commentService.createComment(user, request, id));
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @AuthenticationPrincipal UserEntity user, @RequestBody CommentRequest request) {
        return ResponseEntity.status(200).body(commentService.editComment(id, user, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        commentService.deleteComment(id, user);
        return ResponseEntity.status(200).body(new MessageResponse("Delete new: " + id));
    }
}
