package com.example.mod7.adapter.reposotory;

import com.example.mod7.domain.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
