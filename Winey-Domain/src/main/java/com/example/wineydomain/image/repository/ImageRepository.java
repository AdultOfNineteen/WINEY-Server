package com.example.wineydomain.image.repository;

import com.example.wineydomain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
