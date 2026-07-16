package org.example.authenticationandauthorization.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public record MulticastPushRequestDto(
        @NotEmpty List<String> tokens,
        @NotBlank String title,
        @NotBlank String body,
        Map<String, String> data
) {
}
