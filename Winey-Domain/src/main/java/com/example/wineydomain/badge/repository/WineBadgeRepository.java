package com.example.wineydomain.badge.repository;

import com.example.wineydomain.badge.entity.WineBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineBadgeRepository extends JpaRepository<WineBadge, Long> {
}
