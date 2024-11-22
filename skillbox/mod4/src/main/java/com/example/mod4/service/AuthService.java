package com.example.mod4.service;

import com.example.mod4.adapter.dto.request.JwtAuthenticationResponse;
import com.example.mod4.adapter.dto.request.UserRequest;
import com.example.mod4.config.jwt.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //Регистрация пользователя
    public JwtAuthenticationResponse signUp(UserRequest registrationRequest) {

        var user = userService.createUser(registrationRequest);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getRole());
    }

    //Аутентификация пользователя
    public JwtAuthenticationResponse signIn(UserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getUsername());
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getRole());
    }
}
