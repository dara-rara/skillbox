package com.example.mod7.service;

import com.example.mod7.adapter.reposotory.TaskRepository;
import com.example.mod7.adapter.reposotory.UserRepository;
import com.example.mod7.adapter.web.dto.request.TaskRequest;
import com.example.mod7.adapter.web.dto.response.TaskResponse;
import com.example.mod7.domain.Task;
import com.example.mod7.domain.User;
import com.example.mod7.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Flux<TaskResponse> findAll() {
        return taskRepository.findAll()
                .flatMap(task -> findById(task.getId()))
                .onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> create(TaskRequest request) {
        Task createdTask = taskMapper.toTask(request, Instant.now());
        List<String> specificUserIds = new ArrayList<>(request.getObserverIds());
        specificUserIds.addAll(List.of(request.getAssigneeId(), request.getAuthorId()));

        return taskMapper.toMonoResponse(
                Mono.zip(
                        taskRepository.save(createdTask),
                        userRepository.findAllById(specificUserIds).collectList()
                ).map(data -> taskMapper.toTask(data.getT1(), data.getT2()))
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> findById(String id) {
        return taskMapper.toMonoResponse(
                Mono.zip(
                                taskRepository.findById(id),
                                getUsersByTaskId(id, null)
                        )
                        .map(data -> taskMapper.toTask(data.getT1(), data.getT2()))
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> update(String id, TaskRequest request) {
        return taskMapper.toMonoResponse(
                taskRepository.findById(id)
                        .flatMap(taskForUpdate -> {
                            List<String> newUserIds = new ArrayList<>(request.getObserverIds());
                            newUserIds.addAll(List.of(request.getAssigneeId(), request.getAuthorId()));
                            final Mono<List<User>> allMonoUsers = getUsersByTaskId(id, newUserIds);
                            final Mono<Task> savedTask = taskRepository.save(taskMapper.toUpdatedTask(taskForUpdate, request));
                            return Mono.zip(savedTask, allMonoUsers)
                                    .map(data -> taskMapper.toTask(data.getT1(), data.getT2()));
                        })
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> addObserver(String id, String observerId) {
        return taskMapper.toMonoResponse(
                taskRepository.findById(id)
                        .flatMap(updatedTask -> {
                            updatedTask.getObserverIds().add(observerId);
                            return Mono.zip(
                                    taskRepository.save(updatedTask),
                                    getUsersByTaskId(id, null),
                                    userRepository.findById(observerId)
                            ).map(data -> {
                                data.getT3();
                                return taskMapper.toTask(data.getT1(), data.getT2());
                            });
                        })
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    private Mono<List<User>> getUsersByTaskId(String taskId, List<String> optionalIds) {
        return taskRepository.findById(taskId)
                .flatMap(task -> userRepository.findAllById(getUserIdsByTask(task, optionalIds)).collectList());
    }

    private Set<String> getUserIdsByTask(Task task, List<String> optionalIds) {
        final Set<String> userIds = task.getObserverIds();
        userIds.add(task.getAssigneeId());
        userIds.add(task.getAuthorId());
        if (!CollectionUtils.isEmpty(optionalIds) && !optionalIds.contains(null)) {
            userIds.addAll(optionalIds);
        }
        return userIds;
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

}
