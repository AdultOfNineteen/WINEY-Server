package com.example.wineydomain.verificationMessage.repository;

import com.example.wineydomain.verificationMessage.entity.VerificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationMessageRepository extends JpaRepository<VerificationMessage, Long> {
    Optional<VerificationMessage> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}