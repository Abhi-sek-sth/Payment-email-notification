package org.example.authenticationandauthorization.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record PushNotificationRequestDto(
        @NotBlank String token,
        @NotBlank String title,
        @NotBlank String body,
        Map<String, String> data
) {
}
