package org.example.authenticationandauthorization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.PaymentRequestDto;
import org.example.authenticationandauthorization.dto.response.ApiResponse;
import org.example.authenticationandauthorization.dto.response.PaymentResponseDto;
import org.example.authenticationandauthorization.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<PaymentResponseDto>> paymentVerification(@Valid @RequestBody PaymentRequestDto paymentRequestDto){
        PaymentResponseDto paymentResponseDto = paymentService.verifyPayment(paymentRequestDto);
        return ResponseEntity.ok(ApiResponse.success("successfully verified",paymentResponseDto));
    }
}
