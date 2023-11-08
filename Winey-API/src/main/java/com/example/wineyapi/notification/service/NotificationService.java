package com.example.wineyapi.notification.service;

import com.example.wineydomain.user.entity.User;
import com.example.wineyinfrastructure.firebase.dto.NotificationRequestDto;

import java.util.List;

public interface NotificationService {
    void saveNotification(List<NotificationRequestDto> notificationRequestDtos, User user);
}
