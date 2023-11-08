package com.example.wineydomain.user.repository;

import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFcmTokenRepository extends JpaRepository<UserFcmToken, Long> {
    void deleteByUserAndDeviceId(User user, String deviceId);
}
