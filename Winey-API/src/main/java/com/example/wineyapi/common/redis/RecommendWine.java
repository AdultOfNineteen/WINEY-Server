package com.example.wineyapi.common.redis;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import com.example.wineyapi.wine.dto.WineResponse;
import com.example.wineydomain.wine.repository.WineRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "recommend_wine")
public class RecommendWine {
	@Id
	private String id;

	private List<WineResponse.RecommendWineDTO> recommendWineList;
	@TimeToLive
	private long ttl;
}
