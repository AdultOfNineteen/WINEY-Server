package com.example.wineydomain.image.repository;

import com.example.wineydomain.image.entity.WineImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineImageRepository extends JpaRepository<WineImage, Long> {
}
