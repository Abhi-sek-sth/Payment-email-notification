package org.example.authenticationandauthorization.util;

import org.example.authenticationandauthorization.exception.OtpExpiredException;
import org.example.authenticationandauthorization.exception.OtpInvalidException;
import org.example.authenticationandauthorization.exception.OtpNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OtpStore {

    private static class OtpEntry {
        String otp;
        LocalDateTime expiry;

        OtpEntry(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }
    }

    private final Map<String, OtpEntry> store = new ConcurrentHashMap<>();

    public void save(String email, String otp) {
        store.put(email, new OtpEntry(otp, LocalDateTime.now().plusSeconds(25)));
    }

    public void verify(String email, String otp) {
        OtpEntry entry = store.get(email);

        if (entry == null) {
            throw new OtpNotFoundException("No OTP request found  ");
        }

        if (LocalDateTime.now().isAfter(entry.expiry)) {
            store.remove(email);
            throw new OtpExpiredException("OTP has expired");
        }

        if (!entry.otp.equals(otp)) {
            throw new OtpInvalidException("Invalid OTP entered");
        }

        store.remove(email); // one-time use, success
    }
}