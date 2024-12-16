package com.example.mod4.adapter.web;


import com.example.mod4.adapter.dto.request.UserRequest;
import com.example.mod4.adapter.dto.response.MessageResponse;
import com.example.mod4.adapter.dto.response.UserResponse;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.service.UserService;
import com.example.mod4.service.aop.UserAccessCheck;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/api/user/", produces = APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @UserAccessCheck(action = "READ")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getByUserId(id));
    }

    @PutMapping("/{id}")
    @UserAccessCheck(action = "UPDATE")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @UserAccessCheck(action = "DELETE")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("Content delete"));
    }
}