package com.example.wineydomain.user.repository;

import com.example.wineydomain.user.entity.SocialType;
import com.example.wineydomain.user.entity.UserExitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UserExitHistoryRepository extends JpaRepository<UserExitHistory, Long> {
    boolean existsBySocialIdAndSocialTypeAndCreatedAtGreaterThanEqual(String socialId, SocialType socialType, LocalDateTime createdAt);
}
