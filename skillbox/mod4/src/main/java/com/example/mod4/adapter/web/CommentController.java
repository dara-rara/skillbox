package com.example.mod4.adapter.web;

import com.example.mod4.adapter.dto.request.CommentRequest;
import com.example.mod4.adapter.dto.request.NewsRequest;
import com.example.mod4.adapter.dto.response.MessageResponse;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.CommentService;
import com.example.mod4.service.aop.UserAccessCheck;
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

    @PostMapping("/{id}")
    public ResponseEntity<?> createComment(@PathVariable Long id, @AuthenticationPrincipal UserEntity user,
                                           @RequestBody CommentRequest request) {
        return ResponseEntity.status(201).body(commentService.createComment(user, request, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PutMapping("/{id}")
    @UserAccessCheck(action = "UPDATE_COMMENT")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return ResponseEntity.status(200).body(commentService.editComment(id, request));
    }

    @DeleteMapping("/{id}")
    @UserAccessCheck(action = "DELETE")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(200).body(new MessageResponse("Delete new: " + id));
    }
}
