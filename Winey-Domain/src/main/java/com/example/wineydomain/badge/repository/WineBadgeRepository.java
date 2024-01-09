package com.example.wineydomain.badge.repository;

import java.util.List;

import com.example.wineydomain.badge.entity.WineBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineBadgeRepository extends JpaRepository<WineBadge, Long>, WineBadgeCustomRepository {
	List<WineBadge> findByOrderByIdAsc();
}
