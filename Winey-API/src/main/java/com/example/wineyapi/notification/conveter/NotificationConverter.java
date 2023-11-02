package com.example.wineyapi.notification.conveter;

import com.example.wineydomain.notification.entity.Notification;
import com.example.wineydomain.notification.entity.NotificationType;
import com.example.wineydomain.user.entity.User;
import com.example.wineyinfrastructure.firebase.dto.NotificationRequestDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter {

    public Notification toNotificationBadge(NotificationRequestDto notificationRequestDto, User user, NotificationType type) {
        return Notification.builder()
                .type(type)
                .user(user)
                .title(notificationRequestDto.getTitle())
                .body(notificationRequestDto.getBody())
                .build();
    }
}
