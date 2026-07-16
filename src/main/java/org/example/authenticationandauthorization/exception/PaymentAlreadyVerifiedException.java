package org.example.authenticationandauthorization.exception;

public class PaymentAlreadyVerifiedException extends RuntimeException {
    public PaymentAlreadyVerifiedException(String message) {
        super(message);
    }
}
