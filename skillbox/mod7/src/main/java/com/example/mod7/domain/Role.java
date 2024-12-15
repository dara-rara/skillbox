package com.example.mod7.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    USER,
    EDIT;

    public static GrantedAuthority toAuthority(Role role) {
        return new SimpleGrantedAuthority(role.name());
    }
}
