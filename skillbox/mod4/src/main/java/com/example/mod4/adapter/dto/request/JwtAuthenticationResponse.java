package com.example.mod4.adapter.dto.request;

import com.example.mod4.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private final String token;
    private final Role role;
}