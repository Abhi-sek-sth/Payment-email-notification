package org.example.authenticationandauthorization.exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.example.authenticationandauthorization.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalException implements AuthenticationEntryPoint, AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                new ApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation failed",
                        errors,
                        LocalDateTime.now()
                )
        );
    }

    // ✅ BAD REQUEST (400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
        );
    }

    // ✅ NOT FOUND (404)
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage())
        );
    }

    // ✅ CONFLICT (409)
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(AlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.error(HttpStatus.CONFLICT.value(), ex.getMessage())
        );
    }

    // ✅ UNAUTHORIZED (401 - login failure)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),(ex.getMessage()))
        );
    }
    @ExceptionHandler(OtpInvalidException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidOtpException(OtpInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
        );
    }
    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredOtpException(OtpExpiredException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(
                ApiResponse.error(HttpStatus.GONE.value(), ex.getMessage())
        );
    }
    @ExceptionHandler(OtpNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleOtpNotFoundException(OtpNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage())
        );
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> paymentVerifiedException(PaymentAlreadyVerifiedException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(HttpStatus.CONFLICT.value(),ex.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> paymentVerificationException(PaymentVerificationException ex){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.error(HttpStatus.BAD_GATEWAY.value(), ex.getMessage()));
    }
    @ExceptionHandler(PushNotificationException.class)
    public ResponseEntity<ApiResponse<Void>> handle(PushNotificationException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage())
);
    }

    // 🔥 401 UNAUTHORIZED (JWT / no token)
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ApiResponse<Void> apiResponse =
                ApiResponse.error(HttpStatus.UNAUTHORIZED.value(),
                        "Unauthorized - Invalid or missing token");

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }

    // 🔥 403 FORBIDDEN (role issue)
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ApiResponse<Void> apiResponse =
                ApiResponse.error(HttpStatus.FORBIDDEN.value(),
                        "Access Denied - Insufficient permissions");

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error")
        );
    }
}
