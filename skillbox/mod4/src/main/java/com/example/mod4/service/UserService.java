package com.example.mod4.service;

import com.example.mod4.adapter.dto.request.UserRequest;
import com.example.mod4.adapter.dto.response.UserResponse;
import com.example.mod4.domain.user.Role;
import com.example.mod4.domain.user.UserEntity;
import com.example.mod4.adapter.repository.UserRepository;
import com.example.mod4.service.mapper.UserMapper;
import com.example.mod4.service.error.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> findAll() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    public UserEntity createUser(UserRequest registrationRequest) {
        if (usernameExists(registrationRequest.getUsername())) {
            throw new UsernameNotFoundException(registrationRequest.getUsername());
        }
        var newUser = new UserEntity();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }

    public boolean usernameExists(String name) {
        return userRepository.existsByUsername(name);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserEntity getByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("News not found"));
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }
}
