package com.example.wineyinfrastructure.firebase.service;

import com.example.wineyinfrastructure.firebase.dto.NotificationRequestDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    @Async("push_fcm_messaging")
    public void sendNotification(NotificationRequestDto notificationRequestDto){
        Notification notification = Notification
                .builder()
                .setTitle(notificationRequestDto.getTitle())
                .setBody(notificationRequestDto.getBody())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationRequestDto.getToken())
                .setNotification(notification)
                .build();
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
