package org.example.authenticationandauthorization.dto.request;

import jakarta.validation.constraints.*;
import java.util.Set;

public record UsersRequestDto(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100)
        String name,

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50)
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20)
        String password,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotEmpty(message = "At least one role is required")
        Set<String> roles

) {
}