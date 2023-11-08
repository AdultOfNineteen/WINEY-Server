package com.example.wineyapi.notification.service;

import com.example.wineyapi.notification.conveter.NotificationConverter;
import com.example.wineydomain.notification.entity.Notification;
import com.example.wineydomain.notification.entity.NotificationType;
import com.example.wineydomain.notification.repository.NotificationRepository;
import com.example.wineydomain.user.entity.User;
import com.example.wineyinfrastructure.firebase.dto.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationConverter notificationConverter;
    private final NotificationRepository notificationRepository;

    @Override
    @Async("save_notification")
    public void saveNotification(List<NotificationRequestDto> notificationRequestDtos, User user) {
        List<Notification> notifications = new ArrayList<>();
        for(NotificationRequestDto notificationRequestDto : notificationRequestDtos){
            notifications.add(notificationConverter.toNotificationBadge(notificationRequestDto, user, NotificationType.BADGE));
        }
        notificationRepository.saveAll(notifications);
    }
}
