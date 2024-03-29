package com.example.wineyapi.wineBadge.service;

import com.example.wineydomain.badge.dto.WineBadgeUserDTO;
import com.example.wineydomain.badge.entity.UserWineBadge;
import com.example.wineydomain.user.entity.User;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface WineBadgeService {
    void calculateBadge(User user, Long userId);

    void provideFirstAnalysis(User user);

    void checkActivityBadge(User user);

    List<WineBadgeUserDTO> getWineBadgeListByUser(Long userId);

    WineBadgeUserDTO getWineBadgeById(Long userId, Long wineBadgeId);

    void uploadBadgeImage(Long badgeId, MultipartFile activateImg, MultipartFile unActivateImg);

    //void uploadBadgeImage(Long badgeId, MultipartFile multipartFile);
}
