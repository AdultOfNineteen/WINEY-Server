package com.example.wineydomain.notification.repository;

import com.example.wineydomain.notification.entity.Notification;
import com.example.wineydomain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	void deleteByUser(User user);
}
