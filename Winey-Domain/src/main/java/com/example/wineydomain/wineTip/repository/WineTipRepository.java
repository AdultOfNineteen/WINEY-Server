package com.example.wineydomain.wineTip.repository;

import com.example.wineydomain.wineTip.entity.WineTip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineTipRepository extends JpaRepository<WineTip, Long> {
    Page<WineTip> findByOrderByCreatedAtDesc(Pageable pageable);

}
