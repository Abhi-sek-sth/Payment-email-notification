package org.example.authenticationandauthorization.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PaymentRequestDto(
        @NotBlank
        String pidx
) {
}
