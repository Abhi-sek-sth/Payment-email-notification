package org.example.authenticationandauthorization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.EmailOtp;
import org.example.authenticationandauthorization.dto.request.EmailRequestDto;
import org.example.authenticationandauthorization.dto.response.ApiResponse;
import org.example.authenticationandauthorization.dto.response.EmailResponseDto;
import org.example.authenticationandauthorization.service.EmailService;
import org.example.authenticationandauthorization.service.SendEmail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {
    private final EmailService emailService;
    private final SendEmail sendEmail;
    @PostMapping("/email")
    public ResponseEntity<ApiResponse<Void>> sendEmail(@Valid @RequestBody EmailRequestDto emailRequestDto){
          emailService.receivedEmail(emailRequestDto);
        return ResponseEntity.ok(ApiResponse.successfully("Successfully send OTP "));
    }
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> sendEmailOtp(@Valid @RequestBody EmailOtp emailOtp){
        emailService.verifyOtp(emailOtp);
        return ResponseEntity.ok(ApiResponse.successfully("Successfully verified "));
    }
}
