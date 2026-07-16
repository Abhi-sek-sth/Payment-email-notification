package org.example.authenticationandauthorization.dto.response;

import java.math.BigDecimal;

public record PaymentResponseDto(
        String status,
        String transactionId,
        BigDecimal amount
) {
}
