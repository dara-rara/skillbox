package com.example.mod7.service;

import com.example.mod7.adapter.reposotory.UserRepository;
import com.example.mod7.adapter.web.dto.request.UserRequest;
import com.example.mod7.adapter.web.dto.response.UserResponse;
import com.example.mod7.domain.Role;
import com.example.mod7.domain.User;
import com.example.mod7.adapter.exception.UniqueException;
import com.example.mod7.mapper.UserMapper;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service

public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, UserDetails> users = new HashMap<>();

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

        users.put("user", org.springframework.security.core.userdetails.User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build());

        users.put("edit", org.springframework.security.core.userdetails.User.withUsername("edit")
                .password(passwordEncoder.encode("edit"))
                .roles("EDIT")
                .build());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findUserByUsername(username) // Запрос к базе данных
                .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(String.valueOf(user.getRole()))
                        .build());
    }


    public Mono<UserResponse> create(UserRequest request, Role role) {
        if (isUserNameAlreadyExists(request.getUsername())) {
            throw new UniqueException("Name already exists");
        }
        User user = userMapper.toUser(request);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toMonoResponse(userRepository.save(user));
    }
    
    public Mono<UserResponse> update(String id, UserRequest request) {
        return userMapper.toMonoResponse(userRepository.findById(id)
                .flatMap(userForUpdate ->
                        userRepository.save(userMapper.toUpdatedUser(userForUpdate, request))));
    }

    public Mono<UserResponse> findById(String id) {
        return userMapper.toMonoResponse(userRepository.findById(id));
    }

    public Flux<UserResponse> findAll() {
        return userMapper.toFluxResponse(userRepository.findAll());
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private boolean isUserNameAlreadyExists(String username) {
        return nonNull(findUserByUsername(username).share().block());
    }
}


