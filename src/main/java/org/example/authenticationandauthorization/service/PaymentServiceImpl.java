package org.example.authenticationandauthorization.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.PaymentRequestDto;
import org.example.authenticationandauthorization.dto.response.KhaltiLookupResponseDto;
import org.example.authenticationandauthorization.dto.response.PaymentResponseDto;
import org.example.authenticationandauthorization.entity.Payment;
import org.example.authenticationandauthorization.exception.PaymentAlreadyVerifiedException;
import org.example.authenticationandauthorization.exception.PaymentVerificationException;
import org.example.authenticationandauthorization.repository.PaymentRepository;
import org.example.authenticationandauthorization.securityconfig.KhaltiProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.Map;
@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final WebClient webClient;
    private final KhaltiProperties khaltiProperties;
    @Override
    public PaymentResponseDto verifyPayment(PaymentRequestDto paymentRequestDto) {
            if(paymentRepository.findByPidx(paymentRequestDto.pidx()).isPresent()){
                throw new PaymentAlreadyVerifiedException("Payment already verified");
            }

        KhaltiLookupResponseDto response =
                webClient.post()
                        .uri(khaltiProperties.lookupUrl())
                        .header("Authorization", "Key " + khaltiProperties.secretKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("pidx", paymentRequestDto.pidx()))
                        .retrieve()
                        .onStatus(

                                HttpStatusCode::isError,
                                clientResponse -> clientResponse.bodyToMono(String.class)
                                        .map(PaymentVerificationException::new)
                        )
                        .bodyToMono(KhaltiLookupResponseDto.class)
                        .block();
        System.out.println(response);
        if (response == null) {
            throw new PaymentVerificationException("No response from Khalti");
        }

        if (!"Completed".equalsIgnoreCase(response.status())) {
            throw new PaymentVerificationException(
                    "Payment status : " + response.status()
            );}

            Payment payment = Payment.builder()
                    .pidx(response.pidx())
                    .transactionId(response.transactionId())
                    .status(response.status())
                    .amount(BigDecimal.valueOf(response.totalAmount())
                            .divide(BigDecimal.valueOf(100)))
                    .purchaseOrderId(response.purchaseOrderId())
                    .purchaseOrderName(response.purchaseOrderName())
                    .paidAt(LocalDateTime.now())
                    .build();

            paymentRepository.save(payment);
            return new PaymentResponseDto(
                    payment.getStatus(),
                    payment.getTransactionId(),
                    payment.getAmount()
            );
    }
}
