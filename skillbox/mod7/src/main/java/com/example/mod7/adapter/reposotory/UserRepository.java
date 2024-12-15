package com.example.mod7.adapter.reposotory;

import com.example.mod7.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findUserByUsername(String username);
}
