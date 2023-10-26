package com.example.wineydomain.user.repository;

import com.example.wineydomain.user.entity.User;
import com.example.wineydomain.user.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {
    void deleteByUser(User user);
}
