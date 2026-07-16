package org.example.authenticationandauthorization.mapper;

import org.example.authenticationandauthorization.dto.response.UsersResponseDto;
import org.example.authenticationandauthorization.entity.Role;
import org.example.authenticationandauthorization.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface UsersMapper {
    @Mapping(target = "roles", expression = "java(mapRoles(users.getRoles()))")
    UsersResponseDto toDto(Users users);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());
    }
}
