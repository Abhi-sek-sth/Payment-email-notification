package org.example.authenticationandauthorization.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.authenticationandauthorization.dto.request.MulticastPushRequestDto;
import org.example.authenticationandauthorization.dto.request.PushNotificationRequestDto;
import org.example.authenticationandauthorization.exception.PushNotificationException;
import org.example.authenticationandauthorization.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl  implements NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    /** Send to a single device. */
    public String  sendToDevice(PushNotificationRequestDto request) {
        Message message = Message.builder()
                .setToken(request.token())
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.body())
                        .build())
                .putAllData(request.data() != null ? request.data() : Map.of())
                .setAndroidConfig(androidConfig())
                .setApnsConfig(apnsConfig())
                .build();

        try {
            String response = firebaseMessaging.send(message);
            log.info("Successfully sent message: {}", response);
            return response;
        } catch (FirebaseMessagingException e) {
            handleSendError(e, request.token());
            throw new PushNotificationException(
                    "Failed to send notification: " + e.getMessagingErrorCode());
        }
    }

    /** Send the same notification to many devices at once (up to 500 tokens per call). */
    public BatchResponse sendToMultipleDevices(MulticastPushRequestDto request) {
        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(request.tokens())
                .setNotification(Notification.builder()
                        .setTitle(request.title())
                        .setBody(request.body())
                        .build())
                .putAllData(request.data() != null ? request.data() : Map.of())
                .setAndroidConfig(androidConfig())
                .setApnsConfig(apnsConfig())
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
            log.info("Multicast: {} succeeded, {} failed",
                    response.getSuccessCount(), response.getFailureCount());

            // Identify and log which tokens failed (e.g. to prune from your DB)
            if (response.getFailureCount() > 0) {
                List<SendResponse> results = response.getResponses();
                for (int i = 0; i < results.size(); i++) {
                    if (!results.get(i).isSuccessful()) {
                        String badToken = request.tokens().get(i);
                        log.warn("Token failed: {} — {}", badToken,
                                results.get(i).getException().getMessagingErrorCode());
                        // TODO: remove badToken from your persistence layer if UNREGISTERED
                    }
                }
            }
            return response;
        } catch (FirebaseMessagingException e) {
            throw new PushNotificationException("Multicast send failed");
        }
    }

    private AndroidConfig androidConfig() {
        return AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setChannelId("default_channel")
                        .build())
                .build();
    }

    private ApnsConfig apnsConfig() {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setSound("default").build())
                .build();
    }

    private void handleSendError(FirebaseMessagingException e, String token) {
        if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED
                || e.getMessagingErrorCode() == MessagingErrorCode.INVALID_ARGUMENT) {
            log.warn("Token {} is invalid/expired — remove from database", token);
            // TODO: delete token from your user/device token table
        }
    }
}