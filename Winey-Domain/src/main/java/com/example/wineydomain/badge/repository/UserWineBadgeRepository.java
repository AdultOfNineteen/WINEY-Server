package com.example.wineydomain.badge.repository;

import com.example.wineydomain.badge.entity.UserWineBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWineBadgeRepository extends JpaRepository<UserWineBadge, Long> {
}
