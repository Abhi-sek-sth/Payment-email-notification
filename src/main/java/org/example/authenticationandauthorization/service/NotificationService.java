package org.example.authenticationandauthorization.service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.BatchResponse;
import org.example.authenticationandauthorization.dto.request.MulticastPushRequestDto;
import org.example.authenticationandauthorization.dto.request.PushNotificationRequestDto;

public interface NotificationService {
    public String sendToDevice(PushNotificationRequestDto request);
    public BatchResponse sendToMultipleDevices(MulticastPushRequestDto request);

}
