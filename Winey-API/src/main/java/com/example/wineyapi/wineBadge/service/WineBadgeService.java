package com.example.wineyapi.wineBadge.service;

import com.example.wineydomain.user.entity.User;

public interface WineBadgeService {
    void calculateBadge(User user, Long userId);

    void provideFirstAnalysis(User user);

    void checkActivityBadge(User user);
}
