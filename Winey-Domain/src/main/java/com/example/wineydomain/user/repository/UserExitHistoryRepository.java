package com.example.wineydomain.user.repository;

import com.example.wineydomain.user.entity.UserExitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExitHistoryRepository extends JpaRepository<UserExitHistory, Long> {
}
