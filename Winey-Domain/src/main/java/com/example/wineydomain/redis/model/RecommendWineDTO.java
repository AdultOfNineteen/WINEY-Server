package com.example.wineydomain.redis.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class RecommendWineDTO {
	private Long wineId;

	private String name;

	private String country;

	private String type;

	private List<String> varietal;

	private int price;
}