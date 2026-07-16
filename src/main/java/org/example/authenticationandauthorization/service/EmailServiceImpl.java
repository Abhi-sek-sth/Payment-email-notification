package org.example.authenticationandauthorization.service;

import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.EmailOtp;
import org.example.authenticationandauthorization.dto.request.EmailRequestDto;
import org.example.authenticationandauthorization.util.OtpStore;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

private final OtpStore otpStore;
private final SendEmail sendEmail;
    @Override
    public void receivedEmail(EmailRequestDto emailRequestDto) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.save(emailRequestDto.email(),otp);
        sendEmail.sendOtpEmail(emailRequestDto.email(),otp);


    }




    public void verifyOtp(EmailOtp emailOtp) {
         otpStore.verify(emailOtp.email(), emailOtp.otp());
    }
}
