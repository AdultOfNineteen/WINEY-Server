package com.example.wineydomain.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.wineydomain.redis.entity.RecommendWine;

@Repository
public interface RecommendWineRepository extends CrudRepository<RecommendWine, String> {
}
