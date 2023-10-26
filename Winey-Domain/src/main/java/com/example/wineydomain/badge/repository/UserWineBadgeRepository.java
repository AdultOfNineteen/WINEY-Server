package com.example.wineydomain.badge.repository;

import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWineBadgeRepository extends JpaRepository<UserWineBadge, Long> {
    List<UserWineBadge> findByUser(User user);
}
