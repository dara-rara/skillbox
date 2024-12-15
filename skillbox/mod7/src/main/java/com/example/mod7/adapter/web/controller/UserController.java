package com.example.mod7.adapter.web.controller;

import com.example.mod7.adapter.web.dto.request.UserRequest;
import com.example.mod7.adapter.web.dto.response.UserResponse;
import com.example.mod7.domain.Role;
import com.example.mod7.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Flux<UserResponse> getAll() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<UserResponse>> create(@RequestBody UserRequest userDto,
                                                     @RequestParam Role role) {
        return userService.create(userDto, role).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getById(@PathVariable String id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> update(@PathVariable String id, @RequestBody UserRequest request) {
        return userService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return userService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
