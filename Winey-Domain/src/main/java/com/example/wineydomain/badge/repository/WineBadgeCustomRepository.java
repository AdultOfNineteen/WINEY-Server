package com.example.wineydomain.badge.repository;

import com.example.wineydomain.badge.dto.WineBadgeUserDTO;

import java.util.List;

public interface WineBadgeCustomRepository {
    List<WineBadgeUserDTO> findWineBadgesWithUser(Long userId);
}
