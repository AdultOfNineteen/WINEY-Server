package com.example.wineydomain.redis.repository;

import com.example.wineydomain.redis.entity.AccessToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    Optional<AccessToken> findByToken(String token);
}
