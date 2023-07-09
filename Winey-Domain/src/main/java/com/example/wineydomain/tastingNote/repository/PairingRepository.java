package com.example.wineydomain.tastingNote.repository;

import com.example.wineydomain.tastingNote.entity.Pairing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PairingRepository extends JpaRepository<Pairing, Long> {
}
