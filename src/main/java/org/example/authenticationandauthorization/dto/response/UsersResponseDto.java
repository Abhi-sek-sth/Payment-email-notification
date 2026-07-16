package org.example.authenticationandauthorization.dto.response;

import org.example.authenticationandauthorization.entity.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UsersResponseDto(
        Long id,
        String name,
        String email,
        String username,
        String phoneNumber,
        Boolean active,
        Set<String> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
