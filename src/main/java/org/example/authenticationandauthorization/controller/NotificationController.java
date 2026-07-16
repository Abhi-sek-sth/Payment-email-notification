package org.example.authenticationandauthorization.controller;

import com.google.firebase.messaging.BatchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.MulticastPushRequestDto;
import org.example.authenticationandauthorization.dto.request.PushNotificationRequestDto;
import org.example.authenticationandauthorization.dto.response.ApiResponse;
import org.example.authenticationandauthorization.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendToOne(@Valid @RequestBody PushNotificationRequestDto request) {
        return ResponseEntity.ok(ApiResponse.successfully(notificationService.sendToDevice(request)));
    }

    @PostMapping("/send-multicast")
    public ResponseEntity<Map<String, Integer>> sendToMany(
            @Valid @RequestBody MulticastPushRequestDto request) {
        BatchResponse response = notificationService.sendToMultipleDevices(request);
        return ResponseEntity.ok(Map.of(
                "success", response.getSuccessCount(),
                "failure", response.getFailureCount()
        ));
    }

}
