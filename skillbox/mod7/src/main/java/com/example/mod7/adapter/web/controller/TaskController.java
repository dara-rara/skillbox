package com.example.mod7.adapter.web.controller;

import com.example.mod7.adapter.web.dto.request.TaskRequest;
import com.example.mod7.adapter.web.dto.response.TaskResponse;
import com.example.mod7.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_USER')")
    public Flux<TaskResponse> getAll() {
        return taskService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> create(@RequestBody TaskRequest request) {
        return taskService.create(request)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_USER')")
    public Mono<ResponseEntity<TaskResponse>> getTask(@PathVariable String id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> update(@PathVariable String id, @RequestBody TaskRequest request) {
        return taskService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/observer/{obsId}")
    public Mono<ResponseEntity<TaskResponse>> addObserver(@PathVariable String id, @PathVariable String obsId) {
        return taskService.addObserver(id, obsId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
