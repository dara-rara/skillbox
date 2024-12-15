package com.example.mod7.mapper;

import com.example.mod7.adapter.web.dto.request.UserRequest;
import com.example.mod7.adapter.web.dto.response.UserResponse;
import com.example.mod7.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static java.util.Objects.nonNull;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserResponse toResponse(User model);

    default Flux<UserResponse> toFluxResponse(Flux<User> models) {
        return models.map(this::toResponse);
    }

    default Mono<UserResponse> toMonoResponse(Mono<User> model) {
        return model.map(this::toResponse);
    }

    User toUser(UserRequest request);

    default User toUpdatedUser(User user, UserRequest request) {
        if(nonNull(request.getUsername())) {
            user.setUsername(request.getUsername());
        }
        if(nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }
        return user;
    }

}

