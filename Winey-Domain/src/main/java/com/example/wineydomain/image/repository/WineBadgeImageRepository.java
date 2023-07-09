package com.example.wineydomain.image.repository;

import com.example.wineydomain.image.entity.WineBadgeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineBadgeImageRepository extends JpaRepository<WineBadgeImage, Long> {
}
