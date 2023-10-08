package com.example.wineydomain.redis.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "logout_token")
public class AccessToken {
    @Id
    private String token;

    private String userId;

    @TimeToLive
    private long ttl;

    public AccessToken update(String token, long ttl) {
        this.token = token;
        this.ttl = ttl;
        return this;
    }
}
