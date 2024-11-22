package com.example.mod4.adapter.web;

import com.example.mod4.adapter.dto.request.UserRequest;
import com.example.mod4.adapter.dto.response.MessageResponse;
import com.example.mod4.adapter.repository.UserRepository;
import com.example.mod4.service.AuthService;
import com.example.mod4.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/api/auth/", produces = APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/logup")
    public ResponseEntity<?> signUp(@RequestBody UserRequest request) {
        if (userService.usernameExists(request.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Такой логин уже существует!"));
        }
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody UserRequest request) {
        try {
            if (!userService.usernameExists(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Такого логина не существует!"));
            }
            return ResponseEntity.ok(authService.signIn(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Неправильно набран пароль!"));
        }
    }
}
