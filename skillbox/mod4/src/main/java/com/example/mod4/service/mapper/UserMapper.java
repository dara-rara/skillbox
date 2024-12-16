package com.example.mod4.service.mapper;

import com.example.mod4.adapter.dto.request.UserRequest;
import com.example.mod4.adapter.dto.response.UserResponse;
import com.example.mod4.domain.user.Role;
import com.example.mod4.domain.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewMapper.class}
)
public interface UserMapper {
    List<UserResponse> toResponseList(List<UserEntity> models);

    default UserEntity toCreate(UserRequest model, PasswordEncoder passwordEncoder) {
        var newUser = new UserEntity();
        newUser.setUsername(model.getUsername());
        newUser.setPassword(passwordEncoder.encode(model.getPassword()));
        newUser.setRole(Role.ROLE_USER);
        return newUser;
    };

    default UserEntity toUpdated(UserEntity user, UserRequest request) {
        user.setUsername(request.getUsername());
        return user;
    }
}
