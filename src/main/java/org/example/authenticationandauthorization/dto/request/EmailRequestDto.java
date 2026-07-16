package org.example.authenticationandauthorization.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailRequestDto(
        @Email
        @NotNull
        String email
) {
}
