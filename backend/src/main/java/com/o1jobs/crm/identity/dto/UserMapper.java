package com.o1jobs.crm.identity.dto;

import com.o1jobs.crm.identity.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
