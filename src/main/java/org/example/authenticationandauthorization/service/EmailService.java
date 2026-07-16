package org.example.authenticationandauthorization.service;

import org.example.authenticationandauthorization.dto.request.EmailOtp;
import org.example.authenticationandauthorization.dto.request.EmailRequestDto;
import org.example.authenticationandauthorization.dto.response.EmailResponseDto;

public interface EmailService {
    void receivedEmail(EmailRequestDto emailRequestDto);
     void verifyOtp(EmailOtp emailOtp) ;

    }
