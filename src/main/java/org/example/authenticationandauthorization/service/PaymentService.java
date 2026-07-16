package org.example.authenticationandauthorization.service;

import org.example.authenticationandauthorization.dto.request.PaymentRequestDto;
import org.example.authenticationandauthorization.dto.response.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto verifyPayment(PaymentRequestDto paymentRequestDto);
}
