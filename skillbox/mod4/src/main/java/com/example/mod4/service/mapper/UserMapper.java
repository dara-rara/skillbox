package com.example.mod4.service.mapper;

import com.example.mod4.adapter.dto.response.UserResponse;
import com.example.mod4.domain.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewMapper.class}
)
public interface UserMapper {
    List<UserResponse> toResponseList(List<UserEntity> models);
}
