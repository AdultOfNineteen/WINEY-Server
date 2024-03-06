package com.example.wineyapi.common.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendWineRepository extends CrudRepository<RecommendWine, String> {
}
