package com.example.wineydomain.wine.repository;

import com.example.wineydomain.wine.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
}
